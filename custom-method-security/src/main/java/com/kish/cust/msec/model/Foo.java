package com.kish.cust.msec.model;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Foo {
    private  Long id =-1L;
    private  String data;
    public Foo(String data){
        this.data = data;
    }
}
