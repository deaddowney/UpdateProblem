# Clojure Core Update Problem
Reproducing an issue with using a function named update that clashes with clojure.core/update

Project Downstream depends on project Deps.  Project Deps uses korma 0.4.0 and clojure 1.7.0-alpha1

Project Downstream uses clojure 1.7.0-master-SNAPSHOT and Korma 0.4.1, both of which work around the update problem.

To build, run from the top level

    mvn install
When Downstream compiles, it runs into an issue with update.

    Exception in thread "main" java.lang.RuntimeException: No such var: k/update, compiling:(ktest/korma_test.clj:49:7)
        at clojure.lang.Compiler.analyze(Compiler.java:6535)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler$InvokeExpr.parse(Compiler.java:3729)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6727)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler$BodyExpr$Parser.parse(Compiler.java:5853)
        at clojure.lang.Compiler$LetExpr$Parser.parse(Compiler.java:6171)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6725)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6713)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler$BodyExpr$Parser.parse(Compiler.java:5853)
        at clojure.lang.Compiler$LetExpr$Parser.parse(Compiler.java:6171)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6725)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6713)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler$IfExpr$Parser.parse(Compiler.java:2772)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6725)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler$BodyExpr$Parser.parse(Compiler.java:5853)
        at clojure.lang.Compiler$LetExpr$Parser.parse(Compiler.java:6171)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6725)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6713)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6713)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6713)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler$BodyExpr$Parser.parse(Compiler.java:5853)
        at clojure.lang.Compiler$FnMethod.parse(Compiler.java:5288)
        at clojure.lang.Compiler$FnExpr.parse(Compiler.java:3917)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6723)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6713)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.access$300(Compiler.java:38)
        at clojure.lang.Compiler$DefExpr$Parser.parse(Compiler.java:577)
        at clojure.lang.Compiler.analyzeSeq(Compiler.java:6725)
        at clojure.lang.Compiler.analyze(Compiler.java:6516)
        at clojure.lang.Compiler.analyze(Compiler.java:6477)
        at clojure.lang.Compiler.compile1(Compiler.java:7311)
        at clojure.lang.Compiler.compile(Compiler.java:7382)
        at clojure.lang.RT.compile(RT.java:398)
        at clojure.lang.RT.load(RT.java:438)
        at clojure.lang.RT.load(RT.java:411)
        at clojure.core$load$fn__5384.invoke(core.clj:5850)
        at clojure.core$load.doInvoke(core.clj:5849)
        at clojure.lang.RestFn.invoke(RestFn.java:408)
        at clojure.core$load_one.invoke(core.clj:5655)
        at clojure.core$compile$fn__5389.invoke(core.clj:5861)
        at clojure.core$compile.invoke(core.clj:5860)
        at clojure.lang.Var.invoke(Var.java:379)
        at clojure.lang.Compile.main(Compile.java:67)
    Caused by: java.lang.RuntimeException: No such var: k/update
        at clojure.lang.Util.runtimeException(Util.java:221)
        at clojure.lang.Compiler.resolveIn(Compiler.java:6995)
        at clojure.lang.Compiler.resolve(Compiler.java:6965)
        at clojure.lang.Compiler.analyzeSymbol(Compiler.java:6926)
        at clojure.lang.Compiler.analyze(Compiler.java:6498)
        ... 59 more


If we remove the dependency on Deps, it compiles without issue