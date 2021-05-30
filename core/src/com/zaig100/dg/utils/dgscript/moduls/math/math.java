package com.zaig100.dg.utils.dgscript.moduls.math;

import com.zaig100.dg.utils.dgscript.lib.Function;
import com.zaig100.dg.utils.dgscript.lib.Functions;
import com.zaig100.dg.utils.dgscript.lib.NumberVal;
import com.zaig100.dg.utils.dgscript.lib.Value;
import com.zaig100.dg.utils.dgscript.lib.Variables;
import com.zaig100.dg.utils.dgscript.moduls.Module;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

public class math implements Module {

    private static final DoubleFunction<NumberVal> doubleToNum = value -> new NumberVal(value);

    @Override
    public void init() {
        Functions.set("abs", funcConv(Math::abs));
        Functions.set("cos", funcConv(Math::cos));
        Functions.set("sin", funcConv(Math::sin));
        Functions.set("sqrt", funcConv(Math::sqrt));
        Functions.set("toDegrees", funcConv(Math::toDegrees));
        Functions.set("toRadians", funcConv(Math::toRadians));

        Functions.set("pow", biFuncConv(Math::pow));
        Functions.set("atan2", biFuncConv(Math::atan2));

        Variables.set("S_PI", new NumberVal(3.14));
        Variables.set("PI", new NumberVal(Math.PI));
        Variables.set("S_E", new NumberVal(2.72));
        Variables.set("E", new NumberVal(Math.E));
        Variables.set("G_R", new NumberVal(1.618));

    }

    private static Function funcConv(DoubleUnaryOperator op) {
        return new Function() {
            @SuppressWarnings("NewApi")
            @Override
            public Value execute(Value... args) {
                if (args.length == 1) return doubleToNum.apply(op.applyAsDouble(args[0].asNum()));
                throw new RuntimeException("Argument count exeption");
            }
        };
    }

    private static Function biFuncConv(DoubleBinaryOperator op) {
        return new Function() {
            @SuppressWarnings("NewApi")
            @Override
            public Value execute(Value... args) {
                if (args.length == 2)
                    return doubleToNum.apply(op.applyAsDouble(args[0].asNum(), args[1].asNum()));
                throw new RuntimeException("Argument count exeption");
            }
        };
    }

}
