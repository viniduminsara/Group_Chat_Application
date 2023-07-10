package lk.ijse.chatApp.dto;

import lombok.*;
import java.io.InputStream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class UserDTO {

    private String employeeId;
    private String username;
    private InputStream image;

}
