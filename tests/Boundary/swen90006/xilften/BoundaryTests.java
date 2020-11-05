package swen90006.xilften;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

public class BoundaryTests
{
    protected Xilften xilften;

    @Before public void setUp()
    {
        xilften = new Xilften();
    }

    /*
     * Boundary test cases of the register methond
     */

    /*
     * The boundary value of EC1:
     * passwords.containsKey(username) == false ⋀ 
     * username.length() >= 4 ⋀ 
     * containOnlyLettersAndDigits(username) == true
     *
     * on point:
     * passwords.containsKey(username) == false ⋀
     * username.length() == 4 ⋀
     * containOnlyLettersAndDigits(username) == true
     */
    @Test public void register_EC1_on_point()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("user", "password");
    }

    /*  
     * The boundary value of EC1:
     * passwords.containsKey(username) == false ⋀ 
     * username.length() >= 4 ⋀ 
     * containOnlyLettersAndDigits(username) == true
     *
     * off point:
     * passwords.containsKey(username) == false ⋀
     * username.length() == 3 ⋀
     * containOnlyLettersAndDigits(username) == false
     */
    @Test(expected = InvalidUsernameException.class)
    public void register_EC1_off_point()
    throws DuplicateUserException, InvalidUsernameException
    {   
        xilften.register("###", "password");
    }

    /*  
     * The boundary value of EC2:
     * passwords.containsKey(username) == false ⋀ 
     * username.length() >= 4 ⋀ 
     * containOnlyLettersAndDigits(username) == false
     *
     * on point:
     * passwords.containsKey(username) == false ⋀
     * username.length() == 4 ⋀
     * containOnlyLettersAndDigits(username) == false
     */
    @Test(expected = InvalidUsernameException.class)
    public void register_EC2_on_point()
    throws DuplicateUserException, InvalidUsernameException
    {   
        xilften.register("####", "password");
    }

    /*
     * The boundary value of EC2:
     * passwords.containsKey(username) == false ⋀
     * username.length() >= 4 ⋀
     * containOnlyLettersAndDigits(username) == false
     *
     * off point:
     * passwords.containsKey(username) == false ⋀
     * username.length() == 3 ⋀
     * containOnlyLettersAndDigits(username) == true
     */
    @Test(expected = InvalidUsernameException.class)
    public void register_EC2_off_point()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("usr", "password");
    }

    /*
     * The boundary value of EC3:
     * passwords.containsKey(username) == false ⋀
     * username.length() < 4
     *
     * on point:
     * on point is the same as the on point of EC1
     */

    /*
     * The boundary value of EC3:
     * passwords.containsKey(username) == false ⋀
     * username.length() < 4
     *
     * off point:
     * on point is the same as the on point of EC1
     */


    /*
     * The boundary value of EC4:
     * passwords.containsKey(username) == true
     *
     * on point:
     * passwords.containsKey(username) == true
     */
    @Test(expected = DuplicateUserException.class) 
    public void register_EC4_on_point()
    throws DuplicateUserException, InvalidUsernameException
    {
        xilften.register("username", "password");
        xilften.register("username", "password");
    }

    /*
     * The boundary value of EC4:
     * passwords.containsKey(username) == true
     *
     * off point:
     * off point is the same as the off point of EC1
     */

    /*
     * Boundary test cases of the rent methond
     */

    /*
     * The boundary value of EC1:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is counted
     *
     * on point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period == 5 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_EC1_on_point()
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
     * The boundary value of EC1:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is counted
     *
     * off point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period == 6 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_EC1_off_point()
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
     * The boundary value of EC2:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is not counted
     *
     * on point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period == 5 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_EC2_on_point()
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
     * The boundary value of EC2:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is not counted
     *
     * off point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period == 6 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_EC2_off_point()
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
     * The boundary value of EC3:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is counted
     *
     * on point 1:
     * on point 1 is the same as the on point of EC1
     */

    /*
     * The boundary value of EC3:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * 20 >= period > 5 ⋀ 
     * 29 February is counted
     *
     * on point 2:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period == 20 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_EC3_on_point2()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(2, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 5.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * The boundary value of EC3:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is counted
     *
     * off point 1:
     * off point 1 is the same as the off point of EC1
     */

    /*
     * The boundary value of EC3:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * 20 >= period > 5 ⋀ 
     * 29 February is counted
     *
     * off point 2:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period == 21 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_EC3_off_point2()
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
     * The boundary value of EC4:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is not counted
     *
     * on point 1:
     * on point 1 is the same as the on point of EC2
     */

    /*
     * The boundary value of EC4:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * 20 >= period > 5 ⋀ 
     * 29 February is not counted
     *
     * on point 2:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period == 20 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_EC4_on_point2()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(1, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 5.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * The boundary value of EC4:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is not counted
     *
     * off point 1:
     * off point 1 is the same as the off point of EC2
     */

    /*
     * The boundary value of EC4:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * 20 >= period > 5 ⋀ 
     * 29 February is not counted
     *
     * off point 2:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period == 21 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_EC4_off_point2()
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
     * The boundary value of EC5:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * period > 20 ⋀
     * 29 February is counted
     *
     * on point:
     * on point is the same as the on point 2 of EC3
     */

    /*
     * The boundary value of EC5:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * period > 20 ⋀
     * 29 February is counted
     *
     * off point:
     * off point is the same as the off point 2 of EC3
     */
    
    /*
     * The boundary value of EC6:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * period > 20 ⋀
     * 29 February is not counted
     *
     * on point:
     * on point is the same as the on point 2 of EC4
     */

    /*
     * The boundary value of EC6:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * period > 20 ⋀
     * 29 February is not counted
     *
     * off point:
     * off point is the same as the off point 2 of EC4
     */

    /*
     * The boundary value of EC7:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is counted
     *
     * on point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period == 5 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_EC7_on_point()
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
     * The boundary value of EC7:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is counted
     *
     * off point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period == 6 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_EC7_off_point()
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
     * The boundary value of EC8:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is not counted
     *
     * on point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period == 5 ⋀ 
     * 29 February is not counted
     */
    @Test public void rent_EC8_on_point()
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
     * The boundary value of EC8:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period <= 5 ⋀ 
     * 29 February is not counted
     *
     * off point:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.SD ⋀ 
     * period == 6 ⋀ 
     * 29 February is counted
     */
    @Test public void rent_EC8_off_point()
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
     * The boundary value of EC9:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * 20 >= period > 5 ⋀ 
     * 29 February is counted
     *
     * on point 1:
     * on point 1 is the same as the on point of EC7
     */

     /*
     * The boundary value of EC9:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is counted
     *
     * on point 2:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 == period ⋀
     * 29 February is counted
     */
    @Test public void rent_EC9_on_point2()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(2, 2, 2016);
        Date endDate = new Date(1, 3, 2016);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 6.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * The boundary value of EC9:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is counted
     *
     * off point 1:
     * off point 1 is the same as the off point of EC7
     */

    /*
     * The boundary value of EC9:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is counted
     *
     * off point 2:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * 21 == period ⋀
     * 29 February is not counted
     */
    @Test public void rent_EC9_off_point2()
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
     * The boundary value of EC10:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * 20 >= period > 5 ⋀ 
     * 29 February is not counted
     *
     * on point 1:
     * on point 1 is the same as the on point of EC8
     */

    /*
     * The boundary value of EC10:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is not counted
     *
     * on point 2:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 == period ⋀
     * 29 February is not counted
     */
    @Test public void rent_EC10_on_point2()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(1, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");

        final double expected = 6.5;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
    }

    /*
     * The boundary value of EC10:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is not counted
     *
     * off point 1:
     * off point 1 is the same as the off point of EC8
     */

     /*
     * The boundary value of EC10:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * 20 >= period > 5 ⋀
     * 29 February is not counted
     *
     * off point 2:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.SD ⋀
     * 21 == period ⋀
     * 29 February is counted
     */
    @Test public void rent_EC10_off_point2()
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
     * The boundary value of EC11:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period > 20 ⋀ 
     * 29 February is counted
     *
     * on point:
     * on point is the same as the on point 2 of EC9
     */

     /*
     * The boundary value of EC11:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * period > 20 ⋀
     * 29 February is counted
     *
     * off point:
     * off point is the same as the off point 2 of EC9
     */

    /*
     * The boundary value of EC12:
     * passwords.containsKey(username) == true ⋀ 
     * passwords.get(username).equals(password) == true ⋀ 
     * streamType == StreamType.HD ⋀ 
     * period > 20 ⋀ 
     * 29 February is not counted
     *
     * on point:
     * on point is the same as the on point 2 of EC10
     */

    /*
     * The boundary value of EC12:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == true ⋀
     * streamType == StreamType.HD ⋀
     * period > 20 ⋀
     * 29 February is not counted
     *
     * off point:
     * off point is the same as the off point 2 of EC10
     */
    
    /*  
     * The boundary value of EC13:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == false
     *
     * on point:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == false
     */
    @Test(expected = IncorrectPasswordException.class)
    public void rent_EC13_on_point()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(1, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.register("username", "password");
        xilften.rent("username", "passwords", currentDate, endDate, streamType);
    }

    /*
     * The boundary value of EC13:
     * passwords.containsKey(username) == true ⋀
     * passwords.get(username).equals(password) == false
     *
     * off point:
     * off point is the same as the on point of EC1
     */

    /*
     * The boundary value of EC14:
     * passwords.containsKey(username) == false
     *
     * on point:
     * passwords.containsKey(username) == false
     */
    @Test(expected = NoSuchUserException.class)
    public void rent_EC14_on_point()
    throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
    {
        Date currentDate = new Date(1, 2, 2017);
        Date endDate = new Date(1, 3, 2017);
        Xilften.StreamType streamType = Xilften.StreamType.HD;
        xilften.rent("username", "password", currentDate, endDate, streamType);
    }

    /*
     * The boundary value of EC14:
     * passwords.containsKey(username) == false
     *
     * off point:
     * off point is the same as the on point of EC1
     */

    /*
     *  Test cases to kill mutants
     */

     @Test public void register_usernameContainsOnlyLettersAndDigits()
     throws DuplicateUserException, InvalidUsernameException
     {
        xilften.register("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", "password");
     }

     @Test public void rent_rentFromDecToJan()
     throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
     {
        Date currentDate = new Date(30, 12, 2019);
        Date endDate = new Date(7, 1, 2020);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
     }

     @Test public void rent_rentFromJanToFebInLeapYears()
     throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
     {
        Date currentDate = new Date(27, 1, 2020);
        Date endDate = new Date(4, 2, 2020);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
     }

     @Test public void rent_yearMod100IsNotEqualTo0()
     throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
     {
        Date currentDate = new Date(26, 2, 1900);
        Date endDate = new Date(6, 3, 1900);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4.1;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
     }

     @Test public void rent_periodIsNotEqualToMinimumRentalTime()
     throws NoSuchUserException, IncorrectPasswordException, DuplicateUserException, InvalidUsernameException
     {
        Date currentDate = new Date(31, 8, 2020);
        Date endDate = new Date(1, 9, 2020);
        Xilften.StreamType streamType = Xilften.StreamType.SD;
        xilften.register("username", "password");

        final double expected = 4;
        final double actual = xilften.rent("username", "password", currentDate, endDate, streamType);

        assertEquals(expected, actual, 0.0);
     }
}
