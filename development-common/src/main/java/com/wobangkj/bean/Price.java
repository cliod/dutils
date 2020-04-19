package com.wobangkj.bean;

import com.wobangkj.utils.BeanUtils;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 价格,金额
 *
 * @author cliod
 * @since 2019/9/23
 * package : com.wobangkj.git.cliod.api.as
 */
@EqualsAndHashCode(callSuper = true)
public class Price extends BigDecimal implements Cloneable, Serializable {

    private static final long serialVersionUID = 7033046004897927033L;

    private final BigDecimal amount;

    //region 构造方法
    private Price() {
        super(0);
        this.amount = BigDecimal.ZERO;
    }

    public Price(@NotNull BigDecimal amount) {
        super(amount.doubleValue());
        this.amount = amount;
    }

    public Price(@NotNull Price amount) {
        super(amount.doubleValue());
        this.amount = amount.amount;
    }

    public Price(char[] chars, int i, int i1) {
        super(chars, i, i1);
        this.amount = new BigDecimal(chars, i, i1);
    }

    public Price(char[] chars) {
        super(chars);
        this.amount = new BigDecimal(chars);
    }

    public Price(String s) {
        super(s);
        this.amount = new BigDecimal(s);
    }

    public Price(double v) {
        super(v);
        this.amount = BigDecimal.valueOf(v);
    }

    public Price(BigInteger bigInteger) {
        super(bigInteger);
        this.amount = new BigDecimal(bigInteger);
    }

    public Price(BigInteger bigInteger, int i) {
        super(bigInteger, i);
        this.amount = new BigDecimal(bigInteger, i);
    }

    public Price(int i) {
        super(i);
        this.amount = new BigDecimal(i);
    }

    public Price(long l) {
        super(l);
        this.amount = new BigDecimal(l);
    }
    //endregion

    //region 静态代替构造方法
    @NotNull
    public static Price of(char[] chars) {
        return new Price(chars);
    }

    @NotNull
    public static Price of(@NotNull Number number) {
        return new Price(number.doubleValue());
    }

    @NotNull
    public static Price of(@NotNull BigDecimal number) {
        return new Price(number);
    }

    @NotNull
    public static Price of(@NotNull Price number) {
        return new Price(number);
    }

    @NotNull
    public static Price of(@NotNull CharSequence var) {
        return new Price(var.toString());
    }

    @NotNull
    @Deprecated
    public static Price of(@NotNull Object obj) {
        return new Price(obj.toString());
    }

    @NotNull
    public static Price of() {
        return new Price();
    }
    //endregion

    @NotNull
    public static Price summation(@NotNull Iterable<Price> prices) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (Price price : prices) {
            bigDecimal = bigDecimal.add(price.amount);
        }
        return Price.of(bigDecimal);
    }

    //endregion

    @NotNull
    public static Price accumulation(@NotNull Iterable<Price> prices) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (Price price : prices) {
            bigDecimal = bigDecimal.multiply(price.amount);
        }
        return Price.of(bigDecimal);
    }

    @NotNull
    public static Price[] toPrice(BigDecimal... decimals) {
        if (BeanUtils.isNull(decimals)) {
            return new Price[0];
        }
        Price[] prices = new Price[decimals.length];
        for (int i = 0; i < decimals.length; i++) {
            prices[i] = Price.of(decimals[i]);
        }
        return prices;
    }

    @NotNull
    public static Collection<Price> toPrice(Collection<BigDecimal> decimals) {
        if (CollectionUtils.isEmpty(decimals)) {
            return new ArrayList<>();
        }
        List<Price> prices = new ArrayList<>();
        Price price;
        for (BigDecimal decimal : decimals) {
            price = Price.of(decimal);
            prices.add(price);
        }
        return prices;
    }

    //region get方法
    public BigDecimal get() {
        return this.amount;
    }
    //endregion

    //region Number转化
    @Override
    public long longValue() {
        return this.amount.longValue();
    }

    @Override
    public int intValue() {
        return this.amount.intValue();
    }

    @Override
    public float floatValue() {
        return this.amount.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.amount.doubleValue();
    }

    //region 运算操作
    public Price add(@NotNull Price price) {
        return Price.of(this.amount.add(price.amount));
    }

    public Price multiply(@NotNull Price price) {
        return Price.of(this.amount.multiply(price.amount));
    }

    public Price divide(@NotNull Price price) {
        return Price.of(this.amount.divide(price.amount, RoundingMode.HALF_UP));
    }
    //endregion

    public Price reduce(@NotNull Price price) {
        return Price.of(this.amount.add(price.amount.negate()));
    }

    /**
     * 余数
     *
     * @param price 金钱
     * @return 余数
     */
    public Price remainder(@NotNull Price price) {
        return Price.of(this.amount.remainder(price.amount));
    }

    public Price[] divideAndRemainder(@NotNull Price divisor) {
        BigDecimal[] decimals = this.amount.divideAndRemainder(divisor.amount);
        return toPrice(decimals);
    }

    @Override
    public Price pow(int i) {
        return Price.of(this.amount.pow(i));
    }

    //region 其他操作(转化,比较等)
    @Override
    public Price setScale(int i) {
        return Price.of(this.amount.setScale(i, RoundingMode.HALF_UP));
    }

    @Override
    public BigInteger toBigInteger() {
        return this.amount.toBigInteger();
    }

    @Override
    public Price abs() {
        return Price.of(this.amount.abs());
    }

    @Override
    public Price plus() {
        return this;
    }

    /**
     * 取反
     *
     * @return 反
     */
    @Override
    public Price negate() {
        return Price.of(this.amount.negate());
    }

    public Price min(@NotNull Price price) {
        return this.compareTo(price) <= 0 ? this : price;
    }

    public Price max(@NotNull Price price) {
        return this.compareTo(price) >= 0 ? this : price;
    }

    public Price summation(Price... prices) {
        return summation(new ArrayList<>(Arrays.asList(prices)));
    }

    public Price accumulation(Price... prices) {
        return accumulation(new ArrayList<>(Arrays.asList(prices)));
    }

    @Override
    public int compareTo(@NotNull BigDecimal price) {
        if (price instanceof Price) {
            return this.amount.compareTo(((Price) price).amount);
        }
        return this.amount.compareTo(price);
    }

    public int compare(@NotNull BigDecimal price, @NotNull BigDecimal t1) {
        if (price instanceof Price) {
            return price.compareTo(t1);
        }
        return price.compareTo(t1);
    }
    //endregion

    @Override
    public Price clone() {
        try {
            return (Price) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return Price.of(this);
    }

    public Price copy() {
        return this.clone();
    }

    @Override
    public String toString() {
        if (BeanUtils.isNull(this.amount)) {
            return ((BigDecimal) this).toString();
        }
        return this.amount.toString();
    }
}
