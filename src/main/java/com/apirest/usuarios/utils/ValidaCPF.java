package com.apirest.usuarios.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.http.HttpStatus;

public class ValidaCPF {

  public static boolean isCPF(String cpf) {
    try {
      URL url = new URL("https://user-info.herokuapp.com/users/" + cpf);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();

      if (con.getResponseCode() == HttpStatus.NOT_FOUND.value()) {
        return false;
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }


}
