/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Login;

/**
 *
 * @author mario
 */
public class RespuestaLogin {
    private String token;

    public RespuestaLogin(String token) {
        this.token = token;
    }
    public RespuestaLogin() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
