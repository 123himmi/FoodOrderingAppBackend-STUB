package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import java.time.ZonedDateTime;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerAdminBusinessService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    public CustomerAdminBusinessService() {
    }

    @Transactional
    public CustomerEntity getCustomerById(final Integer customerId) {
        return this.customerDao.getCustomerById(customerId);
    }

    @Transactional
    public CustomerEntity signup(CustomerEntity customerEntity) throws SignUpRestrictedException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (customerEntity.getFirstName() != null && customerEntity.getEmail() != null && customerEntity.getContactNumber() != null && customerEntity.getPassword() != null) {
            if (!pattern.matcher(customerEntity.getEmail()).matches()) {
                throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
            } else if (customerEntity.getContactNumber().matches("[0-9]+") && customerEntity.getContactNumber().length() == 10) {
                if (customerEntity.getPassword().length() >= 8 && customerEntity.getPassword().matches(".*[0-9]{1,}.*") && customerEntity.getPassword().matches(".*[A-Z]{1,}.*") && customerEntity.getPassword().matches(".*[#@$%&*!^]{1,}.*")) {
                    if (this.customerDao.getCustomerByContactNumber(customerEntity.getContactNumber()) != null) {
                        throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
                    } else {
                        String[] encryptedText = this.cryptographyProvider.encrypt(customerEntity.getPassword());
                        customerEntity.setSalt(encryptedText[0]);
                        customerEntity.setPassword(encryptedText[1]);
                        return this.customerDao.createCustomer(customerEntity);
                    }
                } else {
                    throw new SignUpRestrictedException("SGR-004", "Weak password!");
                }
            } else {
                throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
            }
        } else {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }
    }

    @Transactional
    public CustomerAuthEntity authenticate(final String contactNumber, final String password) throws AuthenticationFailedException {
        CustomerEntity customerEntity = this.customerDao.getCustomerByContactNumber(contactNumber);
        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        } else {
            PasswordCryptographyProvider var10000 = this.cryptographyProvider;
            String encryptedPassword = PasswordCryptographyProvider.encrypt(password, customerEntity.getSalt());
            if (encryptedPassword.equals(customerEntity.getPassword())) {
                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
                CustomerAuthEntity customerAuthTokenEntity = new CustomerAuthEntity();
                customerAuthTokenEntity.setCustomer(customerEntity);
                ZonedDateTime now = ZonedDateTime.now();
                ZonedDateTime expiresAt = now.plusHours(8L);
                customerAuthTokenEntity.setAccessToken(jwtTokenProvider.generateToken(customerAuthTokenEntity.getUuid(), now, expiresAt));
                customerAuthTokenEntity.setLoginAt(now);
                customerAuthTokenEntity.setExpiresAt(expiresAt);
                customerAuthTokenEntity.setUuid(customerEntity.getUuid());
                this.customerDao.createAuthToken(customerAuthTokenEntity);
                this.customerDao.updateCustomer(customerEntity);
                return customerAuthTokenEntity;
            } else {
                throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
            }
        }
    }

    @Transactional
    public CustomerEntity updateCustomer(CustomerEntity updatedCustomerEntity, final String authorizationToken) throws AuthorizationFailedException, UpdateCustomerException {
        CustomerAuthEntity customerAuthTokenEntity = this.customerDao.getCustomerAuthToken(authorizationToken);
        this.validateAccessToken(authorizationToken);
        CustomerEntity customerEntity = this.customerDao.getCustomerByUuid(customerAuthTokenEntity.getUuid());
        if (updatedCustomerEntity.getFirstName() == null) {
            throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
        } else {
            customerEntity.setFirstName(updatedCustomerEntity.getFirstName());
            customerEntity.setLastName(updatedCustomerEntity.getLastName());
            this.customerDao.updateCustomer(customerEntity);
            return customerEntity;
        }
    }

    @Transactional
    public CustomerEntity updateCustomerPassword(final String oldPassword, final String newPassword, final String authorizationToken) throws AuthorizationFailedException, UpdateCustomerException {
        ZonedDateTime now = ZonedDateTime.now();
        CustomerAuthEntity customerAuthTokenEntity = this.customerDao.getCustomerAuthToken(authorizationToken);
        this.validateAccessToken(authorizationToken);
        CustomerEntity customerEntity = this.customerDao.getCustomerByUuid(customerAuthTokenEntity.getUuid());
        if (oldPassword != null && newPassword != null) {
            PasswordCryptographyProvider var10000 = this.cryptographyProvider;
            String encryptedPassword = PasswordCryptographyProvider.encrypt(oldPassword, customerEntity.getSalt());
            if (!encryptedPassword.equals(customerEntity.getPassword())) {
                throw new UpdateCustomerException("UCR-004", "Incorrect old password!");
            } else if (newPassword.length() >= 8 && newPassword.matches(".*[0-9]{1,}.*") && newPassword.matches(".*[A-Z]{1,}.*") && newPassword.matches(".*[#@$%&*!^]{1,}.*")) {
                customerEntity.setPassword(newPassword);
                String[] encryptedText = this.cryptographyProvider.encrypt(customerEntity.getPassword());
                customerEntity.setSalt(encryptedText[0]);
                customerEntity.setPassword(encryptedText[1]);
                this.customerDao.updateCustomer(customerEntity);
                return customerEntity;
            } else {
                throw new UpdateCustomerException("UCR-001", "Weak password!");
            }
        } else {
            throw new UpdateCustomerException("UCR-003", "No field should be empty");
        }
    }

    @Transactional
    public CustomerAuthEntity logout(final String authorizationToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthTokenEntity = this.customerDao.getCustomerAuthToken(authorizationToken);
        ZonedDateTime now = ZonedDateTime.now();
        this.validateAccessToken(authorizationToken);
        customerAuthTokenEntity.setLogoutAt(now);
        this.customerDao.updateCustomerAuth(customerAuthTokenEntity);
        return customerAuthTokenEntity;
    }

    @Transactional
    public void validateAccessToken(final String authorizationToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthTokenEntity = this.customerDao.getCustomerAuthToken(authorizationToken);
        ZonedDateTime now = ZonedDateTime.now();
        if (customerAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        } else if (customerAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        } else if (now.isAfter(customerAuthTokenEntity.getExpiresAt())) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
    }

    @Transactional
    public CustomerAuthEntity getCustomerAuthToken(final String accessToken) {
        return this.customerDao.getCustomerAuthToken(accessToken);
    }
}
