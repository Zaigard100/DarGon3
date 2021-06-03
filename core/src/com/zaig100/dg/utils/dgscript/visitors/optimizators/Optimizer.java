package com.zaig100.dg.utils.dgscript.visitors.optimizators;

import com.zaig100.dg.utils.dgscript.ast.Statement;

public class Optimizer {
    public interface Info {
        public int optimizCount();

        public String sumInfo();
    }

    public static Statement optimize(Statement st) {
        int iterOptiCount = 0, level = 1;
        final ConstantFolding constantFolding = new ConstantFolding();
        Statement result = st;
        do {
            level++;
            iterOptiCount = constantFolding.optimizCount();
            result = (Statement) result.accept(constantFolding, null);
        } while ((constantFolding.optimizCount() - iterOptiCount) > 0);
        System.out.println("Levels: " + level);
        System.out.println(constantFolding.sumInfo());
        return result;
    }

}
