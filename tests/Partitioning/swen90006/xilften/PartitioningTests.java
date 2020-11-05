package swen90006.xilften;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

public class PartitioningTests
{
    protected Xilften xilften;

    //Any method annotated with "@Before" will be executed before each test,
    //allowing the tester to set up some shared resources.
    @Before public void setUp()
    {
	xilften = new Xilften();
    }

    //Any method annotated with "@After" will be executed after each test,
    //allowing the tester to release any shared resources used in the setup.
    @After public void tearDown()
    {
    }


    /*
     * Test cases for register method
     */
    
    /*
     * EC1:
     * passwords.containsKey(username) == true ⋀ 
     * username.length() < 4 ⋀ 
     * containOnlyLettersAndDigits(username) == true
     */
    @Test public void register_usernameContainsOnlyLettersAndDigits_nothing()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("username", "password");
    }

    /*
     * EC2:
     * passwords.containsKey(username) == true ⋀ 
     * username.length() < 4 ⋀ 
     * containOnlyLettersAndDigits(username) == false
     */
    @Test(expected = InvalidUsernameException.class)
    public void register_usernameContainsNotOnlyLettersAndDigits_InvalidUsernameExceptionThrown()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("username#1", "password");
    }

    /*
     * EC3:
     * passwords.containsKey(username) == true ⋀ 
     * username.length() < 4
     */
    @Test(expected = InvalidUsernameException.class)
    public void register_lengthOfUsernameIsLessThan4_InvalidUsernameExceptionThrown()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("123", "password");
    }

    /*
     * EC4:
     * passwords.containsKey(username) == true
     */
    @Test(expected = DuplicateUserException.class)
    public void register_repeatedRegister_DuplicateUserExceptionThrown()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("username1", "password1");
        xilften.register("username1", "password1");
    }

    /*
     * Test cases for rent method
     */
    
    /*
     * EC1
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_streamTypeIsSDFor5WeekdaysIncludingFeb29_4()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(23, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC2
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_streamTypeIsSDFor5WeekdaysiNotIncludingFeb29_4()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {   
        Date currentDate = new Date(22, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC3
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * 20 => period > 5 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_streamTypeIsSDFor6WeekdaysIncludingFeb29_4point1()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(22, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC4
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * 20 => period > 5 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_streamTypeIsSDFor6WeekdaysiNotIncludingFeb29_4point1()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(21, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC5
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period > 20 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_streamTypeIsSDFor21WeekdaysIncludingFeb29_5point5()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(1, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 5.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC6
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period > 20 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_streamTypeIsSDFor21WeekdaysiNotIncludingFeb29_5point5()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(31, 1, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 5.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC7
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_streamTypeIsHDFor5WeekdaysIncludingFeb29_5()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(23, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC8
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_streamTypeIsHDFor5WeekdaysiNotIncludingFeb29_5()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {   
        Date currentDate = new Date(22, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC9
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * 20 => period > 5 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_streamTypeIsHDFor6WeekdaysIncludingFeb29_5point1()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(22, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 5.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }
    
    /*
     * EC10
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 => period > 5 ⋀
     * 29 February is not counted
     */
    @Test public void rent_streamTypeIsHDFor6WeekdaysiNotIncludingFeb29_5point1()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(21, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 5.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC11
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * period > 20 ⋀
     * 29 February is counted
     */
    @Test public void rent_streamTypeIsHDFor21WeekdaysIncludingFeb29_6point5()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(1, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 6.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC12
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * period > 20 ⋀
     * 29 February is not counted
     */
    @Test public void rent_streamTypeIsHDFor21WeekdaysiNotIncludingFeb29_6point5()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(31, 1, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 6.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * EC13:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == false
     */
    @Test(expected = IncorrectPasswordException.class)
    public void rent_passwordIsIncorrectForThisUser_IncorrectPasswordExceptionThrown()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(31, 12, 2020);
        Date endDate = new Date(5, 1, 2021);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password1");

        xilften.rent("username", "password", currentDate, endDate, streamType);
    }

    /*
     * EC14:
     * passwords.containsKey(username) == false
     */
    @Test(expected = NoSuchUserException.class)
    public void rent_userDoesNotHaveAXilftenAccount_NoSuchUserExceptionThrown()
    throws NoSuchUserException, IncorrectPasswordException
    {
        Date currentDate = new Date(31, 12, 2020);
        Date endDate = new Date(5, 1, 2021);
        Xilften.StreamType streamType = Xilften.StreamType.HD;

        xilften.rent("username", "password", currentDate, endDate, streamType);
    }
}
