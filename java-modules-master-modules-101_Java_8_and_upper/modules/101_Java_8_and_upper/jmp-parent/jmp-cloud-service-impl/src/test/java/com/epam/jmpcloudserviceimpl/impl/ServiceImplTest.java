package com.epam.jmpcloudserviceimpl.impl;

import com.epam.jmpdto.dto.BankCard;
import com.epam.jmpdto.dto.Subscription;
import com.epam.jmpdto.dto.User;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServiceImplTest extends TestCase {

    private ServiceImpl service;
    private String firstCardNumber;
    private BankCard firstBankCardForFirstUser;
    private BankCard secondBankCardForFirstUser;
    private BankCard secondBankCard;
    private User firstUser;
    private User secondUser;

    protected void setUp() {
        service = new ServiceImpl();
        firstCardNumber = "1234-5678-9012-3456";
        firstUser = new User("George", "Tester", LocalDate.of(1987, 5, 16));
        secondUser = new User("Anna", "Banker", LocalDate.of(1990, 11, 20));
        firstBankCardForFirstUser = new BankCard(firstCardNumber, firstUser);
        secondBankCardForFirstUser = new BankCard("5934-0391-8349-1299", firstUser);
        secondBankCard = new BankCard("4856-1385-8483-8834", secondUser);
    }

    public void testSubscribeWithValidBankCard() {
        try {
            service.subscribe(firstBankCardForFirstUser);
            Subscription subscription = service.getSubscriptionByBankCardNumber(firstCardNumber).orElse(null);
            assertNotNull("Subscription should be created", subscription);
            assertEquals("Subscription record should match the bank card used",
                    firstBankCardForFirstUser, subscription.getBankCard());
        } catch (Exception e) {
            fail("No exception should be thrown for a valid subscription");
        }
    }

    public void testSubscribeWithNullBankCard() {
        try {
            service.subscribe(null);
            fail("Expected an IllegalArgumentException to be thrown for null bank card");
        } catch (IllegalArgumentException e) {
            assertEquals("Bank card must not be null", e.getMessage());
        }
    }

    public void testSubscriptionAlreadyExists() {
        service.subscribe(firstBankCardForFirstUser);
        try {
            service.subscribe(firstBankCardForFirstUser);
            fail("Expected an IllegalStateException to be thrown for duplicate subscription");
        } catch (IllegalStateException e) {
            assertEquals("Subscription already exists for this card number!", e.getMessage());
        }
    }

    public void testGetSubscriptionByValidBankCardNumber() {
        service.subscribe(firstBankCardForFirstUser);
        Optional<Subscription> result = service.getSubscriptionByBankCardNumber(firstBankCardForFirstUser.getNumber());
        assertTrue("Subscription should be present", result.isPresent());
        assertEquals("The subscription should be the one added", firstBankCardForFirstUser, result.get().getBankCard());
    }

    public void testGetSubscriptionByNullBankCardNumber() {
        try {
            service.getSubscriptionByBankCardNumber(null);
            fail("Expected an IllegalArgumentException to be thrown for null card number");
        } catch (IllegalArgumentException e) {
            assertEquals("Card number cannot be null, empty or invalid.", e.getMessage());
        }
    }

    public void testGetSubscriptionByEmptyBankCardNumber() {
        try {
            service.getSubscriptionByBankCardNumber("   ");
            fail("Expected an IllegalArgumentException to be thrown for empty card number");
        } catch (IllegalArgumentException e) {
            assertEquals("Card number cannot be null, empty or invalid.", e.getMessage());
        }
    }

    public void testGetSubscriptionByInvalidBankCardNumberFormat() {
        try {
            service.getSubscriptionByBankCardNumber("1234");
            fail("Expected an IllegalArgumentException to be thrown for invalid card number format");
        } catch (IllegalArgumentException e) {
            assertEquals("Card number cannot be null, empty or invalid.", e.getMessage());
        }
    }

    public void testGetSubscriptionByNonExistentBankCardNumber() {
        Optional<Subscription> result = service.getSubscriptionByBankCardNumber("9999-9999-9999-9999");
        assertFalse("Subscription should not be present", result.isPresent());
    }

    public void testGetAllUsersWithSubscription() {
        service.subscribe(firstBankCardForFirstUser);
        service.subscribe(secondBankCard);
        List<User> users = service.getAllUsers();
        assertNotNull("The list of users should not be null", users);
        assertEquals("There should be exactly two distinct users", 2, users.size());
        assertTrue("List should contain the first user", users.contains(firstUser));
        assertTrue("List should contain the second user", users.contains(secondUser));
    }

    public void testGetAllUsersListContainsUniqueUsers() {
        service.subscribe(firstBankCardForFirstUser);
        service.subscribe(secondBankCardForFirstUser);
        service.subscribe(secondBankCard);
        List<User> users = service.getAllUsers();
        assertEquals("There should still be exactly two distinct users", 2, users.size());
    }

    public void testGetAllUsersUnmodifiableList() {
        service.subscribe(firstBankCardForFirstUser);
        List<User> users = service.getAllUsers();
        try {
            users.add(secondUser);
            fail("The list should be unmodifiable and should not allow additions");
        } catch (UnsupportedOperationException e) {
            // Expected result if the operation is unsupported, test passes
        }
    }
}
