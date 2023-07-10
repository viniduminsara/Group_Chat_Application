package lk.ijse.chatApp.model;

import lk.ijse.chatApp.dto.UserDTO;
import lk.ijse.chatApp.util.CrudUtil;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean saveUser(UserDTO userDTO) throws SQLException {
        String query = "INSERT INTO user VALUES (?,?,?)";
        return CrudUtil.execute(query,userDTO.getEmployeeId(),userDTO.getUsername(),userDTO.getImage());
    }

    public static boolean existsUser(String employeeId, String username) throws SQLException {
        String query = "SELECT employeeId FROM user WHERE employeeId = ? OR username = ?";
        ResultSet rs = CrudUtil.execute(query,employeeId,username);
        return rs.next();
    }

    public static boolean existsUser(String username) throws SQLException {
        String query = "SELECT employeeId FROM user WHERE username = ?";
        ResultSet rs = CrudUtil.execute(query,username);
        return rs.next();
    }

    public static UserDTO getUser(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username =?";
        ResultSet rs = CrudUtil.execute(query,username);
        if (rs.next()){
            Blob blob = rs.getBlob(3);
            InputStream inputStream;
            if (blob != null) {
                inputStream = blob.getBinaryStream();
            }else {
                inputStream = null;
            }
            return new UserDTO(rs.getString(1),rs.getString(2),inputStream);
        }
        return null;
    }
}
