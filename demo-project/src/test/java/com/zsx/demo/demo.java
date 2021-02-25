package com.zsx.demo;

import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;

/**
 * @Description TODO
 * @Date 2021/2/25 10:35
 * @Created by zhushuxian
 */
public class demo {
    public static void main(String[] args) throws  Exception {
//        String password = "123456";
//
//        //自定义publicKey
//        String[] arr = CryptoUtils.genKeyPair(512);
//        System.out.println("privateKey:" + arr[0]);
//        System.out.println("publicKey:" + arr[1]);
//        String enpassword = CryptoUtils.encrypt(arr[0], password);
//        System.out.println("password:" + enpassword);
//        System.out.println("password:" + CryptoUtils.decrypt(arr[1], enpassword));

        String enpassword = CryptoUtils.encrypt("MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAxw6pDKIe62T1pNh/s0BoMjfgv4CmZFq0RoQczmK8e1ddxokQU3uU+7+KkRgpneg9CGiXKJhDUbkdnvmNd/hcWwIDAQABAkAX5Qef75ockar3FdIGd3XKiXCeLFhM9aJsUJGQqMONXpI6Ke1QKQpNS2uqApUAUjRORPhopeF9bZK7YqZs9rXpAiEA5xpDP28SwD1RkYy0qzjKjN7qZb0G7HrDZMigSkP70OUCIQDcgJkB/mXzz0MmbivhfEdJpH0phm/qRduFdNfrD+TkPwIgeWc2hJBZDVwpCYkdMAV5gl9oS2HtzTUAiGVMrzbfhnECIQC/hSbg93DgOoatq4WLnWFh0nAwECfGyRRpr1oXPj/mCwIhAJc43vu80xPz7+m2NaC5wahVtsOvWoXesEnaEgb2niLr", "123456789");
        System.out.println("password:" + enpassword);
    }
}
