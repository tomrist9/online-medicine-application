package com.online.medicine.application.order.service.functional;


@FunctionalInterface
public interface QuadFunction<T, U, W, V, R> {
    R apply(T t, U u, W w, V v);
}
