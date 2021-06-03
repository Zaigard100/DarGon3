package com.zaig100.dg.utils.dgscript.visitors.optimizators;

import com.zaig100.dg.utils.dgscript.ast.Statement;

public class Optimizer {
    public interface Info {
        public int optimizCount();

        public String sumInfo();
    }

    public static Statement optimize(Statement st) {
        int iterOptiCount = 0, level = 1;
        final ConstantFolding cF = new ConstantFolding();
        final DeadCodeElimitation dcE = new DeadCodeElimitation();
        Statement result = st;
        do {
            level++;
            iterOptiCount = cF.optimizCount() + dcE.optimizCount();
            result = (Statement) result.accept(cF, null);
            result = (Statement) result.accept(dcE, null);
        } while (((cF.optimizCount() + dcE.optimizCount()) - iterOptiCount) > 0);
        System.out.println("Levels: " + level);
        System.out.println(cF.sumInfo());
        System.out.println(dcE.sumInfo());
        return result;
    }

}
