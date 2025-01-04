package com.rita.product_management.core.domain.user;

import com.rita.product_management.core.common.util.RandomPasswordGenerator;
import com.rita.product_management.core.domain.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class UserTest {

    private static final String SAMPLE_NAME = "John Doe";
    private static final String SAMPLE_EMAIL = "johndoe@example.com";
    private static final UserType SAMPLE_ROLE = UserType.STOCKIST;

    @BeforeEach
    void setup() {
    }

    @Test
    void givenValidNameAndEmail_whenCreatingUser_thenUserShouldBeInitializedCorrectly() {
        String name = SAMPLE_NAME;
        String email = SAMPLE_EMAIL;
        UserType role = SAMPLE_ROLE;
        try (MockedStatic<RandomPasswordGenerator> passwordMock = mockStatic(RandomPasswordGenerator.class)) {
            passwordMock.when(RandomPasswordGenerator::generatePassword).thenReturn("mockedPassword");
            User user = new User(name, email, role);
            assertEquals(name, user.getName());
            assertEquals(email, user.getEmail());
            assertEquals(role, user.getRole());
            assertFalse(user.getActive());
            assertNotNull(user.getUsername());
            assertEquals("mockedPassword", user.getPassword());
            assertNotNull(user.getCreatedAt());
            assertNotNull(user.getUpdatedAt());
        }
    }

    @Test
    void givenInvalidName_whenGeneratingUsername_thenThrowException() {
        String invalidName = "";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> User.generateUsername(invalidName)
        );
        assertEquals("Name cannot be empty for username generation", exception.getMessage());
    }

    @Test
    void givenName_whenGeneratingUsername_thenUsernameIsGeneratedCorrectly() {
        String name = "John Doe";
        String username = User.generateUsername(name);

        assertTrue(username.contains("johndoe"));
    }

    @Test
    void givenValidUsers_whenComparing_thenUsersAreEqual() {
        User user1 = new User(SAMPLE_NAME, SAMPLE_EMAIL, SAMPLE_ROLE);
        User user2 = new User(SAMPLE_NAME, SAMPLE_EMAIL, SAMPLE_ROLE);
        user2.setUsername(user1.getUsername());
        assertEquals(user1, user2);
    }

    @Test
    void givenDifferentRoles_whenComparing_thenUsersAreNotEqual() {
        User user1 = new User(SAMPLE_NAME, SAMPLE_EMAIL, UserType.STOCKIST);
        User user2 = new User(SAMPLE_NAME, SAMPLE_EMAIL, UserType.ADMIN);
        assertNotEquals(user1, user2);
    }

    @Test
    void givenUser_whenHashCodeIsGenerated_thenHashCodeIsConsistent() {
        User user = new User(SAMPLE_NAME, SAMPLE_EMAIL, SAMPLE_ROLE);
        int hashCode1 = user.hashCode();
        int hashCode2 = user.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void givenMultipleUsers_whenTestingContainers_thenEnsureIsolation() {
        User user1 = new User("Alice", "alice@example.com", UserType.STOCKIST);
        User user2 = new User("Bob", "bob@example.com", UserType.ADMIN);
        String username1 = user1.getUsername();
        String username2 = user2.getUsername();
        assertNotEquals(username1, username2);
    }

}
