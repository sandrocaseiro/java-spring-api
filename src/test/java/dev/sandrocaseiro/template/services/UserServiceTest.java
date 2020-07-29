package dev.sandrocaseiro.template.services;

import dev.sandrocaseiro.template.exceptions.AppException;
import dev.sandrocaseiro.template.models.domain.EGroup;
import dev.sandrocaseiro.template.models.domain.ERole;
import dev.sandrocaseiro.template.models.domain.EUser;
import dev.sandrocaseiro.template.models.jpa.JUserGroup;
import dev.sandrocaseiro.template.repositories.UserRepository;
import dev.sandrocaseiro.template.security.IAuthenticationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private IAuthenticationInfo authInfo;

    @InjectMocks
    private UserService userService;

    private static List<EGroup> groups;
    private static List<ERole> roles;
    private static List<EUser> users;

    @BeforeEach
    void init() {
        prepareData();
        prepateMock();
    }

    void prepareData() {
        EGroup group1 = new EGroup(1, "Group 1");
        EGroup group2 = new EGroup(2, "Group 1");

        ERole role1 = new ERole(1, "Role 1");
        ERole role2 = new ERole(2, "Role 2");

        EUser user1 = new EUser(1, "User 1", "66619705022", "user1@mail.com", "1234", BigDecimal.valueOf(100),
            group1.getId(), group1, Collections.singletonList(role1));
        EUser user2 = new EUser(2, "User 2", "19037443001", "user2@mail.com", "1234", BigDecimal.valueOf(10),
            group2.getId(), group2, Collections.singletonList(role2));
        EUser user3 = new EUser(3, "User 3", "87004531023", "user3@mail.com", "1234", BigDecimal.valueOf(55.5),
            group1.getId(), group1, Arrays.asList(role1, role2));
        EUser user4 = new EUser(4, "User 4", "10260842028", "user4@mail.com", "1234", BigDecimal.valueOf(15),
            group2.getId(), group2, Arrays.asList(role1, role2));
        EUser user5 = new EUser(5, "User 5", "29464594039", "user5@mail.com", "1234", BigDecimal.valueOf(10.4),
            group2.getId(), group2, Arrays.asList(role1, role2));
        EUser user6 = new EUser(6, "User 6", "55776072050", "user6@mail.com", "1234", BigDecimal.valueOf(0),
            group2.getId(), group2, Arrays.asList(role1, role2));

        user1.setActive(true);
        user2.setActive(true);
        user3.setActive(true);
        user4.setActive(true);
        user5.setActive(true);

        groups = Arrays.asList(group1, group2);
        roles = Arrays.asList(role1, role2);
        users = Arrays.asList(user1, user2, user3, user4, user5, user6);
    }

    void prepateMock() {
        lenient().when(authInfo.getId()).thenReturn(1);

        lenient().when(userRepository.getOne(any(Integer.class))).thenAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);
            Optional<EUser> user = users.stream().filter(u -> u.getId() == id).findFirst();

            return user.orElse(null);
        });

        lenient().when(userRepository.findByUsername(any(String.class))).thenAnswer(invocationOnMock -> {
            String username = invocationOnMock.getArgument(0);
            Optional<EUser> user = users.stream().filter(u -> u.getEmail().equals(username)).findFirst();

            return user.orElse(null);
        });

        lenient().when(userRepository.findById(any(Integer.class))).thenAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);

            return users.stream().filter(u -> u.getId() == id).findFirst();
        });

        lenient().when(userRepository.findOneById(any(Integer.class))).thenAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);

            return users.stream().filter(u -> u.getId() == id).findFirst().map(u -> new JUserGroup() {
                @Override
                public Integer getId() {
                    return u.getId();
                }

                @Override
                public String getName() {
                    return u.getName();
                }

                @Override
                public String getEmail() {
                    return u.getEmail();
                }

                @Override
                public String getGroup() {
                    return u.getGroup().getName();
                }
            }).orElse(null);
        });

        lenient().when(userRepository.save(any(EUser.class))).then(returnsFirstArg());

        lenient().when(userRepository.updateBalance(any(Integer.class), any(BigDecimal.class))).thenAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgument(0);
            BigDecimal balance = invocationOnMock.getArgument(1);

            Optional<EUser> user = users.stream().filter(u -> u.getId() == id).findFirst();
            if (!user.isPresent())
                return 0;

            user.get().setBalance(balance);
            return 1;
        });
    }

    @Test
    void testCannotCreateUserWithSameEmail() {
        EUser user = new EUser(4, "User 1", "66619705022", "user1@mail.com", "1234", BigDecimal.valueOf(100),
            1, null, null);
        assertThatExceptionOfType(AppException.class).isThrownBy(() -> userService.create(user));
    }

    @Test
    void testCanCreateUser() {
        EUser user = new EUser(4, "User 1", "66619705022", "user999@mail.com", "1234", BigDecimal.valueOf(100),
            1, null, null);
        assertThat(userService.create(user)).isEqualTo(user);
    }

    @Test
    void testCannotUpdateNotExistingUser() {
        EUser user = new EUser(999, "User 1", "66619705022", "user1@mail.com", "1234", BigDecimal.valueOf(100),
            1, null, null);

        assertThatExceptionOfType(AppException.class).isThrownBy(() -> userService.update(user));
    }

    @Test
    void testCanUpdateExistingUser() {
        EUser user = new EUser(1, "User 2", "432423432", "user1@mail.com", "12345", BigDecimal.valueOf(100),
            2, null, Collections.emptyList());

        userService.update(user);
        EUser dbUser = users.get(0);
        assertThat(dbUser.getName()).isEqualTo(user.getName());
        assertThat(dbUser.getCpf()).isEqualTo(user.getCpf());
        assertThat(dbUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(dbUser.getGroupId()).isEqualTo(user.getGroupId());
        assertThat(dbUser.getRoles()).isEmpty();
    }

    @Test
    void testExistingCanUpdateUserBalance() {
        BigDecimal balance = BigDecimal.valueOf(50);

        userService.updateBalance(1, balance);
        EUser user = userRepository.getOne(1);

        assertThat(user.getBalance()).isEqualByComparingTo(balance);
    }

    @Test
    void testUpdateBalanceWithNotExistingUserShouldThrowException() {
        BigDecimal balance = BigDecimal.valueOf(50);

        assertThatExceptionOfType(AppException.class).isThrownBy(() -> userService.updateBalance(999, balance));
    }

    @Test
    void testUserCannotDeleteHimself() {
        assertThatExceptionOfType(AppException.class).isThrownBy(() -> userService.delete(1));
    }

    @Test
    void testUserCannotDeleteNotExistingUser() {
        assertThatExceptionOfType(AppException.class).isThrownBy(() -> userService.delete(999));
    }

    @Test
    void testUserCanDeleteAnotherUser() {
        userService.delete(2);

        assertThat(users.get(1).isActive()).isFalse();
    }

    @Test
    void testFindNotExistingUserShouldThrowException() {
        assertThatExceptionOfType(AppException.class).isThrownBy(() -> userService.findById(999));
    }

    @Test
    void testCanFindUserById() {
        JUserGroup user = userService.findById(3);
        EUser dbUser = users.get(2);

        assertThat(user.getId()).isEqualTo(dbUser.getId());
        assertThat(user.getName()).isEqualTo(dbUser.getName());
        assertThat(user.getGroup()).isEqualTo(dbUser.getGroup().getName());
    }
}
