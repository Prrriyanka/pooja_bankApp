package com.cg.bank.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cg.DAO.UserDao;
import com.cg.DAO.loanDao;
import com.cg.beans.User;
import com.cg.beans.loan;
import com.cg.exceptions.LoanDetailsNotFoundException;
import com.cg.service.UserService;
import com.cg.service.UserServiceImpl;

public class AssignmentPoojaTestJupiter {

    private static UserService service;
    private static UserDao userdaomock;
    private static BCryptPasswordEncoder passwordEncoderMock;
    private static loanDao loanDaoMock;

    @BeforeAll
    public static void setUpTestEnv() {
        userdaomock = Mockito.mock(UserDao.class);
        passwordEncoderMock = Mockito.mock(BCryptPasswordEncoder.class);

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userdaomock);
        userServiceImpl.setPasswordEncoder(passwordEncoderMock);
        service = userServiceImpl;
        
        // Mock loanDao
        loanDaoMock = Mockito.mock(loanDao.class);

        // Stubbing behavior for password encoder
        Mockito.when(passwordEncoderMock.encode(Mockito.anyString()))
               .thenAnswer(invocation -> {
                   String rawPassword = invocation.getArgument(0);
                   // Simulate encoding (replace with your actual encoding logic)
                   return  rawPassword;
               });

        // Setup mock behavior for save method
        User user = new User();
        user.setFname("Priyanka");
        user.setEmail("p@gmail.com");
        user.setPassword("Qwerty@123");
        user.setDOB(new Date(99, 7, 5)); // Date is deprecated, but using for example
        user.setGender("female");
        user.setRole("ROLE_NORMAL_USER");

        loan loan = new loan();
        loan.setLoantype("loan1");
        user.setLoans(Arrays.asList(loan));

        Mockito.when(userdaomock.save(Mockito.any(User.class))).thenReturn(user);
    }

    // 1. Test Case for saveUser
    
    @Test
    public void testAcceptUserDetails() {
        User expectedUser = new User();
        expectedUser.setFname("Priyanka");
        expectedUser.setEmail("p@gmail.com");
        expectedUser.setPassword("Qwerty@123"); // Set the plain text password
        expectedUser.setDOB(new Date(99, 7, 5)); // Date is deprecated, but using for example
        expectedUser.setGender("female");

        loan loan = new loan();
        loan.setLoantype("loan1");
        expectedUser.setLoans(Arrays.asList(loan));

        // Mock behavior for password encoder
        Mockito.when(passwordEncoderMock.encode(Mockito.anyString()))
               .thenAnswer(invocation -> {
                   String rawPassword = invocation.getArgument(0);
                   // Simulate encoding (replace with your actual encoding logic)
                   return  rawPassword;
               });

        // Encode the password in the expectedUser object to match the behavior of saveUser
        expectedUser.setPassword(passwordEncoderMock.encode(expectedUser.getPassword()));

        User insertedUser = service.saveUser(expectedUser);

        // Assert that the returned insertedUser matches the expectedUser,
        // including the encoded password
        Assertions.assertEquals(expectedUser.getFname(), insertedUser.getFname());
        Assertions.assertEquals(expectedUser.getEmail(), insertedUser.getEmail());
        Assertions.assertEquals(expectedUser.getPassword(), insertedUser.getPassword()); // Adjust for encoded password
        Assertions.assertEquals(expectedUser.getDOB(), insertedUser.getDOB());
        Assertions.assertEquals(expectedUser.getGender(), insertedUser.getGender());
        // Add more assertions for other fields as needed
        // Assertions.assertEquals(expectedUser.getloans(), insertedUser.getloans());

        Mockito.verify(userdaomock).save(Mockito.any(User.class));
        
    //  2. Test Case for getAllloansByApprovalStatus
        
        
        // Mock loanDao
        // loanDao loanDaoMock = Mockito.mock(loanDao.class);
         List<loan> loans = new ArrayList<>();
         loan loan1 = new loan();
         loan1.setLoantype("loan1");
         loan loan2 = new loan();
         loan2.setLoantype("loan2");
         loans.add(loan1);
         loans.add(loan2);
         Mockito.when(loanDaoMock.findByApprovalStatus("approved")).thenReturn(loans);

         // Mock loanDao
//       loanDao loanDaoMock = Mockito.mock(loanDao.class);
       Mockito.when(loanDaoMock.findByApprovalStatus("rejected")).thenReturn(Collections.emptyList());

       // 3. Test Case for getAllloans
       
       
    // Mock loanDao
       //loanDao loanDaoMock = Mockito.mock(loanDao.class);
       List<loan> loansthree = new ArrayList<>();
       loan loan1three = new loan();
       loan1three.setLoantype("loan1");
       loan loan2three = new loan();
       loan2three.setLoantype("loan2");
       loansthree.add(loan1three);
       loansthree.add(loan2three);
       Mockito.when(loanDaoMock.findAll()).thenReturn(loans);

        
        
    }

    
    
    //1. Test Case for getAllloansByEmail
    
    
    @Test
    void testGetAllloansByEmail() {
        // Mock loanDao
    
        List<loan> loans = new ArrayList<>();
        loan loan1 = new loan();
        loan1.setLoantype("loan1");
        loan loan2 = new loan();
        loan2.setLoantype("loan2");
        loans.add(loan1);
        loans.add(loan2);
        Mockito.when(loanDaoMock.findAllByUserEmail("test@example.com")).thenReturn(loans);

        // Create UserServiceImpl instance
        UserServiceImpl userService = new UserServiceImpl();
        userService.setloanDao(loanDaoMock);

        // Call the method
        List<loan> foundloans = userService.getAllLoansByEmail("test@example.com");

        // Assertions
        Assertions.assertEquals(2, foundloans.size());
        Assertions.assertEquals("loan1", foundloans.get(0).getLoantype());
        Assertions.assertEquals("loan2", foundloans.get(1).getLoantype());
    }

    @Test
    void testGetAllloansByEmail_EmptyList() {
     
        Mockito.when(loanDaoMock.findAllByUserEmail("nonexistent@example.com")).thenReturn(Collections.emptyList());

        // Create UserServiceImpl instance
        UserServiceImpl userService = new UserServiceImpl();
        userService.setloanDao(loanDaoMock);

        // Call the method
        List<loan> foundloans = userService.getAllLoansByEmail("nonexistent@example.com");

        // Assertions
        Assertions.assertTrue(foundloans.isEmpty());
    }

    
  //  2. Test Case for getAllloansByApprovalStatus
    
    
    @Test
    void testGetAllloansByApprovalStatus() throws LoanDetailsNotFoundException {
       
        // Create UserServiceImpl instance
        UserServiceImpl userService = new UserServiceImpl();
        userService.setloanDao(loanDaoMock);

        // Call the method
        List<loan> foundloans = userService.getAllLoansbyApprovalStatus("approved");

        // Assertions
        Assertions.assertEquals(2, foundloans.size());
        Assertions.assertEquals("loan1", foundloans.get(0).getLoantype());
        Assertions.assertEquals("loan2", foundloans.get(1).getLoantype());
    }

    @Test
    void testGetAllloansByApprovalStatus_NoloansFound() {
          // Create UserServiceImpl instance
        UserServiceImpl userService = new UserServiceImpl();
        userService.setloanDao(loanDaoMock);

        // Call the method and assert exception
        Assertions.assertThrows(LoanDetailsNotFoundException.class, () -> {
            userService.getAllLoansbyApprovalStatus("rejected");
        });
    }

    
    
    // 3. Test Case for getAllloans
    
    @Test
    void testGetAllloans() {
        
        // Create UserServiceImpl instance
        UserServiceImpl userService = new UserServiceImpl();
        userService.setloanDao(loanDaoMock);

        // Call the method
        List<loan> foundloans = userService.getAllLoans();

        // Assertions
        Assertions.assertEquals(2, foundloans.size());
        Assertions.assertEquals("loan1", foundloans.get(0).getLoantype());
        Assertions.assertEquals("loan2", foundloans.get(1).getLoantype());
    }

    

    @AfterAll
    public static void tearDownTestEnv() {
        userdaomock = null;
        service = null;
    }

}
