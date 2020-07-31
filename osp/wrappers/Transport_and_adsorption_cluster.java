/*
 * Transport_and_adsorption_cluster.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jul 22 2020, 13:08 by COMSOL 5.5.0.359. */
public class Transport_and_adsorption_cluster {

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("/home/IWM/runs");

    model.component().create("comp1", true);

    model.component("comp1").geom().create("geom1", 2);

    model.component("comp1").mesh().create("mesh1");

    model.component("comp1").physics().create("tds", "DilutedSpecies", new String[][]{{"c"}});
    model.component("comp1").physics().create("gb", "GeneralFormBoundaryPDE", new String[][]{{"cs"}});

    model.study().create("std1");
    model.study("std1").create("time", "Transient");
    model.study("std1").feature("time").activate("tds", true);
    model.study("std1").feature("time").activate("gb", true);

    model.param().set("c0", "1000[mol/m^3]");
    model.param().set("k_ads", "1e-6[m^3/(mol*s)]");

    model.component("comp1").geom("geom1").run();

    model.param().set("k_des", "1e-9[1/s]");
    model.param().set("Gamma_s", "1000[mol/m^2]");
    model.param().set("D_s", "1e-11[m^2/s]");
    model.param().set("D", "1e-9[m^2/s]");
    model.param().set("v_max", "1[mm/s]");
    model.param().set("delta", "0.1[mm]");

    model.component("comp1").geom("geom1").create("r1", "Rectangle");
    model.component("comp1").geom("geom1").feature("r1").set("type", "solid");
    model.component("comp1").geom("geom1").feature("r1").set("base", "corner");
    model.component("comp1").geom("geom1").feature("r1").set("pos", new String[]{"-0.5", "-0.8"});
    model.component("comp1").geom("geom1").feature("r1").set("size", new String[]{"1", "1.3"});
    model.component("comp1").geom("geom1").run("r1");
    model.component("comp1").geom("geom1").feature("r1").set("size", new int[]{30, 90});
    model.component("comp1").geom("geom1").feature("r1").set("pos", new double[]{0, -0.1});
    model.component("comp1").geom("geom1").runPre("fin");
    model.component("comp1").geom("geom1").run("r1");
    model.component("comp1").geom("geom1").create("pt1", "Point");
    model.component("comp1").geom("geom1").feature("pt1").setIndex("p", 0.1, 0);
    model.component("comp1").geom("geom1").runPre("fin");
    model.component("comp1").geom("geom1").feature("pt1").setIndex("p", 30, 0);
    model.component("comp1").geom("geom1").feature("pt1").setIndex("p", 10, 1);
    model.component("comp1").geom("geom1").runPre("fin");
    model.component("comp1").geom("geom1").run("pt1");
    model.component("comp1").geom("geom1").create("pt2", "Point");
    model.component("comp1").geom("geom1").feature("pt2").setIndex("p", 30, 0);
    model.component("comp1").geom("geom1").feature("pt2").setIndex("p", 10.5, 1);
    model.component("comp1").geom("geom1").runPre("fin");
    model.component("comp1").geom("geom1").feature("pt2").setIndex("p", 15, 1);
    model.component("comp1").geom("geom1").runPre("fin");

    model.component("comp1").variable().create("var1");

    model.component("comp1").geom("geom1").run();

    model.component("comp1").variable("var1").selection().geom("geom1", 1);
    model.component("comp1").variable("var1").selection().set(5);
    model.component("comp1").variable("var1").set("R", "k_ads*c*(Gamma_s-cs)-k_des*cs");
    model.component("comp1").variable().create("var2");
    model.component("comp1").variable("var2").selection().geom("geom1", 2);
    model.component("comp1").variable("var2").selection().set(1);
    model.component("comp1").variable("var2").set("v_lam", "v_max*(1-((x-0.5*delta)/(0.5*delta))^2)");
    model.component("comp1").variable("var2").descr("v_lam", "Inlet velocity profile");
    model.component("comp1").variable("var1").descr("R", "Surface reaction rate");

    model.component("comp1").physics("tds").feature("cdm1")
         .set("D_c", new String[]{"D", "0", "0", "0", "D", "0", "0", "0", "D"});
    model.component("comp1").physics("tds").feature("cdm1").set("u", new String[]{"0", "v_lam", "0"});
    model.component("comp1").physics("tds").feature("init1").setIndex("initc", "c0", 0);
    model.component("comp1").physics("tds").create("conc1", "Concentration", 1);
    model.component("comp1").physics("tds").feature("conc1").setIndex("species", true, 0);
    model.component("comp1").physics("tds").feature("conc1").setIndex("c0", "c0", 0);
    model.component("comp1").physics("tds").create("fl1", "FluxBoundary", 1);
    model.component("comp1").physics("tds").feature("conc1").selection().set(2);
    model.component("comp1").physics("tds").feature("fl1").selection().set(5);
    model.component("comp1").physics("tds").feature("fl1").setIndex("species", true, 0);
    model.component("comp1").physics("tds").feature("fl1").setIndex("J0", "-R", 0);
    model.component("comp1").physics("tds").create("out1", "Outflow", 1);
    model.component("comp1").physics("tds").feature("out1").selection().set(3);
    model.component("comp1").physics("tds").create("sym1", "Symmetry", 1);
    model.component("comp1").physics("tds").feature("sym1").selection().set(1, 4, 6);
    model.component("comp1").physics("gb").selection().set(5);
    model.component("comp1").physics("gb").prop("Units").set("CustomDependentVariableUnit", "1");
    model.component("comp1").physics("gb").prop("Units").set("DependentVariableQuantity", "none");
    model.component("comp1").physics("gb").prop("Units").setIndex("CustomDependentVariableUnit", "mol/m^2", 0, 0);
    model.component("comp1").physics("gb").prop("Units").setIndex("CustomSourceTermUnit", "mol/(m^2*s)", 0, 0);
    model.component("comp1").physics("gb").feature("gfeq1").setIndex("Ga", new String[]{"-csTx*Ds", "-csTy"}, 0);
    model.component("comp1").physics("gb").feature("gfeq1").setIndex("Ga", new String[]{"-csTx*Ds", "-csTy*Ds"}, 0);
    model.component("comp1").physics("gb").feature("gfeq1").setIndex("f", "R", 0);

    model.component("comp1").mesh("mesh1").create("ftri1", "FreeTri");
    model.component("comp1").mesh("mesh1").feature("size").set("custom", false);
    model.component("comp1").mesh("mesh1").feature("ftri1").selection().geom("geom1");
    model.component("comp1").mesh("mesh1").feature("ftri1").selection().geom("geom1", 2);
    model.component("comp1").mesh("mesh1").feature("ftri1").selection().remaining();
    model.component("comp1").mesh("mesh1").feature().remove("ftri1");
    model.component("comp1").mesh("mesh1").create("ftri1", "FreeTri");
    model.component("comp1").mesh("mesh1").feature("ftri1").create("size1", "Size");
    model.component("comp1").mesh("mesh1").feature("ftri1").feature("size1").selection().geom("geom1", 1);
    model.component("comp1").mesh("mesh1").feature("ftri1").feature("size1").selection().set(5);
    model.component("comp1").mesh("mesh1").feature("ftri1").feature("size1").set("custom", true);
    model.component("comp1").mesh("mesh1").feature("ftri1").feature("size1").set("hmaxactive", true);
    model.component("comp1").mesh("mesh1").feature("ftri1").feature("size1").set("hmax", "1.5[um]");
    model.component("comp1").mesh("mesh1").run("size");
    model.component("comp1").mesh("mesh1").feature("ftri1").feature("size1").set("hmax", "1.5[mm]");
    model.component("comp1").mesh("mesh1").run();

    model.study("std1").feature("time").set("tlist", "range(0,0.05,2)");

    model.sol().create("sol1");
    model.sol("sol1").study("std1");

    model.study("std1").feature("time").set("notlistsolnum", 1);
    model.study("std1").feature("time").set("notsolnum", "1");
    model.study("std1").feature("time").set("listsolnum", 1);
    model.study("std1").feature("time").set("solnum", "1");

    model.sol("sol1").create("st1", "StudyStep");
    model.sol("sol1").feature("st1").set("study", "std1");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").create("v1", "Variables");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").create("t1", "Time");
    model.sol("sol1").feature("t1").set("tlist", "range(0,0.05,2)");
    model.sol("sol1").feature("t1").set("plot", "off");
    model.sol("sol1").feature("t1").set("plotgroup", "Default");
    model.sol("sol1").feature("t1").set("plotfreq", "tout");
    model.sol("sol1").feature("t1").set("probesel", "all");
    model.sol("sol1").feature("t1").set("probes", new String[]{});
    model.sol("sol1").feature("t1").set("probefreq", "tsteps");
    model.sol("sol1").feature("t1").set("rtol", 0.005);
    model.sol("sol1").feature("t1").set("atolglobalvaluemethod", "factor");
    model.sol("sol1").feature("t1").set("maxorder", 2);
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").create("seDef", "Segregated");
    model.sol("sol1").feature("t1").create("fc1", "FullyCoupled");
    model.sol("sol1").feature("t1").feature("fc1").set("stabacc", "aacc");
    model.sol("sol1").feature("t1").feature("fc1").set("aaccdim", 5);
    model.sol("sol1").feature("t1").feature("fc1").set("maxiter", 8);
    model.sol("sol1").feature("t1").feature("fc1").set("damp", 0.9);
    model.sol("sol1").feature("t1").feature("fc1").set("jtech", "once");
    model.sol("sol1").feature("t1").create("d1", "Direct");
    model.sol("sol1").feature("t1").feature("fc1").set("linsolver", "d1");
    model.sol("sol1").feature("t1").feature("fc1").set("stabacc", "aacc");
    model.sol("sol1").feature("t1").feature("fc1").set("aaccdim", 5);
    model.sol("sol1").feature("t1").feature("fc1").set("maxiter", 8);
    model.sol("sol1").feature("t1").feature("fc1").set("damp", 0.9);
    model.sol("sol1").feature("t1").feature("fc1").set("jtech", "once");
    model.sol("sol1").feature("t1").feature().remove("fcDef");
    model.sol("sol1").feature("t1").feature().remove("seDef");
    model.sol("sol1").attach("std1");

    model.result().create("pg1", "PlotGroup2D");
    model.result("pg1").label("Concentration (tds)");
    model.result("pg1").set("data", "dset1");
    model.result("pg1").feature().create("surf1", "Surface");
    model.result("pg1").feature("surf1").label("Surface");
    model.result("pg1").feature("surf1").set("smooth", "internal");
    model.result("pg1").feature("surf1").set("data", "parent");
    model.result().create("pg2", "PlotGroup2D");
    model.result("pg2").set("data", "dset1");
    model.result("pg2").create("line1", "Line");
    model.result("pg2").feature("line1").set("expr", "cs");
    model.result().remove("pg2");
    model.result().remove("pg1");

    model.param().rename("D_s", "Ds");

    model.sol("sol1").study("std1");

    model.study("std1").feature("time").set("notlistsolnum", 1);
    model.study("std1").feature("time").set("notsolnum", "1");
    model.study("std1").feature("time").set("listsolnum", 1);
    model.study("std1").feature("time").set("solnum", "1");

    model.sol("sol1").feature().remove("t1");
    model.sol("sol1").feature().remove("v1");
    model.sol("sol1").feature().remove("st1");
    model.sol("sol1").create("st1", "StudyStep");
    model.sol("sol1").feature("st1").set("study", "std1");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").create("v1", "Variables");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").create("t1", "Time");
    model.sol("sol1").feature("t1").set("tlist", "range(0,0.05,2)");
    model.sol("sol1").feature("t1").set("plot", "off");
    model.sol("sol1").feature("t1").set("plotgroup", "Default");
    model.sol("sol1").feature("t1").set("plotfreq", "tout");
    model.sol("sol1").feature("t1").set("probesel", "all");
    model.sol("sol1").feature("t1").set("probes", new String[]{});
    model.sol("sol1").feature("t1").set("probefreq", "tsteps");
    model.sol("sol1").feature("t1").set("rtol", 0.005);
    model.sol("sol1").feature("t1").set("atolglobalvaluemethod", "factor");
    model.sol("sol1").feature("t1").set("maxorder", 2);
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").create("seDef", "Segregated");
    model.sol("sol1").feature("t1").create("fc1", "FullyCoupled");
    model.sol("sol1").feature("t1").feature("fc1").set("stabacc", "aacc");
    model.sol("sol1").feature("t1").feature("fc1").set("aaccdim", 5);
    model.sol("sol1").feature("t1").feature("fc1").set("maxiter", 8);
    model.sol("sol1").feature("t1").feature("fc1").set("damp", 0.9);
    model.sol("sol1").feature("t1").feature("fc1").set("jtech", "once");
    model.sol("sol1").feature("t1").create("d1", "Direct");
    model.sol("sol1").feature("t1").feature("fc1").set("linsolver", "d1");
    model.sol("sol1").feature("t1").feature("fc1").set("stabacc", "aacc");
    model.sol("sol1").feature("t1").feature("fc1").set("aaccdim", 5);
    model.sol("sol1").feature("t1").feature("fc1").set("maxiter", 8);
    model.sol("sol1").feature("t1").feature("fc1").set("damp", 0.9);
    model.sol("sol1").feature("t1").feature("fc1").set("jtech", "once");
    model.sol("sol1").feature("t1").feature().remove("fcDef");
    model.sol("sol1").feature("t1").feature().remove("seDef");
    model.sol("sol1").attach("std1");

    model.result().create("pg1", "PlotGroup2D");
    model.result("pg1").label("Concentration (tds)");
    model.result("pg1").set("showlooplevel", new String[]{"off", "off", "off"});
    model.result("pg1").set("data", "dset1");
    model.result("pg1").feature().create("surf1", "Surface");
    model.result("pg1").feature("surf1").label("Surface");
    model.result("pg1").feature("surf1").set("smooth", "internal");
    model.result("pg1").feature("surf1").set("data", "parent");
    model.result().create("pg2", "PlotGroup2D");
    model.result("pg2").set("data", "dset1");
    model.result("pg2").create("line1", "Line");
    model.result("pg2").feature("line1").set("expr", "cs");

    model.sol("sol1").runAll();

    model.result("pg1").run();
    model.result("pg2").run();
    model.result("pg2").run();
    model.result("pg2").run();
    model.result("pg1").run();
    model.result("pg1").run();
    model.result("pg2").run();
    model.result("pg1").run();
    model.result("pg2").run();
    model.result("pg1").run();
    model.result("pg2").run();
    model.result("pg1").run();
    model.result("pg1").run();
    model.result("pg1").run();
    model.result("pg1").run();
    model.result("pg2").run();
    model.result("pg2").label("Concentration species in reactor");
    model.result("pg2").run();
    model.result().create("pg3", "PlotGroup1D");
    model.result("pg3").run();
    model.result("pg3").label(" Concentration reacting species along active surface");
    model.result("pg3").run();
    model.result("pg3").setIndex("looplevelinput", "manual", 0);
    model.result("pg3").setIndex("looplevel", new int[]{4}, 0);
    model.result("pg3").setIndex("looplevel", new int[]{41}, 0);
    model.result("pg3").create("lngr1", "LineGraph");
    model.result("pg3").feature("lngr1").selection().set(5);
    model.result("pg3").feature("lngr1").set("xdataexpr", "y");
    model.result("pg3").feature("lngr1").set("xdatadescr", "y-coordinate");
    model.result("pg3").run();
    model.result("pg3").run();

    model.label("Transport_and_adsorption_cluster.mph");

    model.result("pg3").run();

    model.sol("sol1").study("std1");

    model.study("std1").feature("time").set("notlistsolnum", 1);
    model.study("std1").feature("time").set("notsolnum", "1");
    model.study("std1").feature("time").set("listsolnum", 1);
    model.study("std1").feature("time").set("solnum", "1");

    model.sol("sol1").feature().remove("t1");
    model.sol("sol1").feature().remove("v1");
    model.sol("sol1").feature().remove("st1");
    model.sol("sol1").create("st1", "StudyStep");
    model.sol("sol1").feature("st1").set("study", "std1");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").create("v1", "Variables");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").create("t1", "Time");
    model.sol("sol1").feature("t1").set("tlist", "range(0,0.05,2)");
    model.sol("sol1").feature("t1").set("plot", "off");
    model.sol("sol1").feature("t1").set("plotgroup", "pg1");
    model.sol("sol1").feature("t1").set("plotfreq", "tout");
    model.sol("sol1").feature("t1").set("probesel", "all");
    model.sol("sol1").feature("t1").set("probes", new String[]{});
    model.sol("sol1").feature("t1").set("probefreq", "tsteps");
    model.sol("sol1").feature("t1").set("rtol", 0.005);
    model.sol("sol1").feature("t1").set("atolglobalvaluemethod", "factor");
    model.sol("sol1").feature("t1").set("maxorder", 2);
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").create("seDef", "Segregated");
    model.sol("sol1").feature("t1").create("fc1", "FullyCoupled");
    model.sol("sol1").feature("t1").feature("fc1").set("stabacc", "aacc");
    model.sol("sol1").feature("t1").feature("fc1").set("aaccdim", 5);
    model.sol("sol1").feature("t1").feature("fc1").set("maxiter", 8);
    model.sol("sol1").feature("t1").feature("fc1").set("damp", 0.9);
    model.sol("sol1").feature("t1").feature("fc1").set("jtech", "once");
    model.sol("sol1").feature("t1").create("d1", "Direct");
    model.sol("sol1").feature("t1").feature("fc1").set("linsolver", "d1");
    model.sol("sol1").feature("t1").feature("fc1").set("stabacc", "aacc");
    model.sol("sol1").feature("t1").feature("fc1").set("aaccdim", 5);
    model.sol("sol1").feature("t1").feature("fc1").set("maxiter", 8);
    model.sol("sol1").feature("t1").feature("fc1").set("damp", 0.9);
    model.sol("sol1").feature("t1").feature("fc1").set("jtech", "once");
    model.sol("sol1").feature("t1").feature().remove("fcDef");
    model.sol("sol1").feature("t1").feature().remove("seDef");
    model.sol("sol1").attach("std1");
    model.sol("sol1").runAll();

    model.result("pg1").run();
    model.result("pg3").run();
    model.result("pg1").run();
    model.result("pg2").run();
    model.result("pg3").run();
    model.result("pg3").run();
    model.result().report().create("rpt1", "Report");
    model.result().report("rpt1").set("templatesource", "complete");
    model.result().report("rpt1").set("format", "docx");
    model.result().report("rpt1").set("filename", "/home/IWM/runs/test_java.docx");
    model.result().report("rpt1").create("tp1", "TitlePage");
    model.result().report("rpt1").create("toc1", "TableOfContents");
    model.result().report("rpt1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec1").set("heading", "Global Definitions");
    model.result().report("rpt1").feature("sec1").create("root1", "Model");
    model.result().report("rpt1").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec1").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec1").feature("sec1").set("heading", "Parameters");
    model.result().report("rpt1").feature("sec1").feature("sec1").create("param1", "Parameter");
    model.result().report("rpt1").feature("sec1").feature("sec1").feature("param1").set("noderef", "default");
    model.result().report("rpt1").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").set("source", "custom");
    model.result().report("rpt1").feature("sec2").set("heading", "Component 1");
    model.result().report("rpt1").feature("sec2").create("comp1", "ModelNode");
    model.result().report("rpt1").feature("sec2").feature("comp1").set("noderef", "comp1");
    model.result().report("rpt1").feature("sec2").feature("comp1").set("includeauthor", true);
    model.result().report("rpt1").feature("sec2").feature("comp1").set("includedatecreated", true);
    model.result().report("rpt1").feature("sec2").feature("comp1").set("includeversion", true);
    model.result().report("rpt1").feature("sec2").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec2").feature("sec1").set("heading", "Definitions");
    model.result().report("rpt1").feature("sec2").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").set("heading", "Variables");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1")
         .set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1")
         .set("heading", "Variables 1");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1")
         .create("var1", "Variables");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1").feature("var1")
         .set("noderef", "var1");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2")
         .set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2")
         .set("heading", "Variables 2");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2")
         .create("var1", "Variables");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2").feature("var1")
         .set("noderef", "var2");
    model.result().report("rpt1").feature("sec2").feature("sec1").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").set("source", "custom");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2")
         .set("heading", "Coordinate Systems");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1")
         .set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1")
         .set("heading", "Boundary System 1");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1")
         .create("csys1", "CoordinateSystem");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1").feature("csys1")
         .set("noderef", "sys1");
    model.result().report("rpt1").feature("sec2").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec2").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec2").set("heading", "Geometry 1");
    model.result().report("rpt1").feature("sec2").feature("sec2").create("geom1", "Geometry");
    model.result().report("rpt1").feature("sec2").feature("sec2").feature("geom1").set("noderef", "geom1");
    model.result().report("rpt1").feature("sec2").create("sec3", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec3").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec3").set("heading", "Transport of Diluted Species");
    model.result().report("rpt1").feature("sec2").feature("sec3").create("phys1", "Physics");
    model.result().report("rpt1").feature("sec2").feature("sec3").feature("phys1").set("noderef", "tds");
    model.result().report("rpt1").feature("sec2").create("sec4", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec4").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec4").set("heading", "General Form Boundary PDE");
    model.result().report("rpt1").feature("sec2").feature("sec4").create("phys1", "Physics");
    model.result().report("rpt1").feature("sec2").feature("sec4").feature("phys1").set("noderef", "gb");
    model.result().report("rpt1").feature("sec2").create("sec5", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec5").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec5").set("heading", "Mesh 1");
    model.result().report("rpt1").feature("sec2").feature("sec5").create("mesh1", "Mesh");
    model.result().report("rpt1").feature("sec2").feature("sec5").feature("mesh1").set("noderef", "mesh1");
    model.result().report("rpt1").create("sec3", "Section");
    model.result().report("rpt1").feature("sec3").set("source", "custom");
    model.result().report("rpt1").feature("sec3").set("heading", "Study 1");
    model.result().report("rpt1").feature("sec3").create("std1", "Study");
    model.result().report("rpt1").feature("sec3").feature("std1").set("noderef", "std1");
    model.result().report("rpt1").feature("sec3").create("sec1", "Section");
    model.result().report("rpt1").feature("sec3").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec3").feature("sec1").set("heading", "Solver Configurations");
    model.result().report("rpt1").feature("sec3").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").set("source", "firstchild");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").set("heading", "Solution 1");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").create("sol1", "Solver");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").feature("sol1")
         .set("noderef", "sol1");
    model.result().report("rpt1").create("sec4", "Section");
    model.result().report("rpt1").feature("sec4").set("source", "custom");
    model.result().report("rpt1").feature("sec4").set("heading", "Results");
    model.result().report("rpt1").feature("sec4").create("sec1", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec4").feature("sec1").set("heading", "Data Sets");
    model.result().report("rpt1").feature("sec4").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1")
         .set("heading", "Study 1/Solution 1");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1").create("dset1", "DataSet");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1").feature("dset1")
         .set("noderef", "dset1");
    model.result().report("rpt1").feature("sec4").create("sec2", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").set("source", "custom");
    model.result().report("rpt1").feature("sec4").feature("sec2").set("heading", "Plot Groups");
    model.result().report("rpt1").feature("sec4").feature("sec2").create("sec1", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1")
         .set("heading", "Concentration (tds)");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1").create("pg1", "PlotGroup");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1").feature("pg1")
         .set("noderef", "pg1");
    model.result().report("rpt1").feature("sec4").feature("sec2").create("sec2", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2")
         .set("heading", "Concentration species in reactor");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2").create("pg1", "PlotGroup");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2").feature("pg1")
         .set("noderef", "pg2");
    model.result().report("rpt1").feature("sec4").feature("sec2").create("sec3", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3")
         .set("heading", " Concentration reacting species along active surface");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3").create("pg1", "PlotGroup");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3").feature("pg1")
         .set("noderef", "pg3");
    model.result().report("rpt1").feature().clear();
    model.result().report("rpt1").create("tp1", "TitlePage");
    model.result().report("rpt1").create("toc1", "TableOfContents");
    model.result().report("rpt1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec1").set("heading", "Global Definitions");
    model.result().report("rpt1").feature("sec1").create("root1", "Model");
    model.result().report("rpt1").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec1").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec1").feature("sec1").set("heading", "Parameters");
    model.result().report("rpt1").feature("sec1").feature("sec1").create("param1", "Parameter");
    model.result().report("rpt1").feature("sec1").feature("sec1").feature("param1").set("noderef", "default");
    model.result().report("rpt1").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").set("source", "custom");
    model.result().report("rpt1").feature("sec2").set("heading", "Component 1");
    model.result().report("rpt1").feature("sec2").create("comp1", "ModelNode");
    model.result().report("rpt1").feature("sec2").feature("comp1").set("noderef", "comp1");
    model.result().report("rpt1").feature("sec2").feature("comp1").set("includeauthor", true);
    model.result().report("rpt1").feature("sec2").feature("comp1").set("includedatecreated", true);
    model.result().report("rpt1").feature("sec2").feature("comp1").set("includeversion", true);
    model.result().report("rpt1").feature("sec2").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec2").feature("sec1").set("heading", "Definitions");
    model.result().report("rpt1").feature("sec2").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").set("heading", "Variables");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1")
         .set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1")
         .set("heading", "Variables 1");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1")
         .create("var1", "Variables");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec1").feature("var1")
         .set("noderef", "var1");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2")
         .set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2")
         .set("heading", "Variables 2");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2")
         .create("var1", "Variables");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec1").feature("sec2").feature("var1")
         .set("noderef", "var2");
    model.result().report("rpt1").feature("sec2").feature("sec1").create("sec2", "Section");

    return model;
  }

  public static Model run2(Model model) {
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").set("source", "custom");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2")
         .set("heading", "Coordinate Systems");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").create("sec1", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1")
         .set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1")
         .set("heading", "Boundary System 1");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1")
         .create("csys1", "CoordinateSystem");
    model.result().report("rpt1").feature("sec2").feature("sec1").feature("sec2").feature("sec1").feature("csys1")
         .set("noderef", "sys1");
    model.result().report("rpt1").feature("sec2").create("sec2", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec2").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec2").set("heading", "Geometry 1");
    model.result().report("rpt1").feature("sec2").feature("sec2").create("geom1", "Geometry");
    model.result().report("rpt1").feature("sec2").feature("sec2").feature("geom1").set("noderef", "geom1");
    model.result().report("rpt1").feature("sec2").create("sec3", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec3").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec3").set("heading", "Transport of Diluted Species");
    model.result().report("rpt1").feature("sec2").feature("sec3").create("phys1", "Physics");
    model.result().report("rpt1").feature("sec2").feature("sec3").feature("phys1").set("noderef", "tds");
    model.result().report("rpt1").feature("sec2").create("sec4", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec4").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec4").set("heading", "General Form Boundary PDE");
    model.result().report("rpt1").feature("sec2").feature("sec4").create("phys1", "Physics");
    model.result().report("rpt1").feature("sec2").feature("sec4").feature("phys1").set("noderef", "gb");
    model.result().report("rpt1").feature("sec2").create("sec5", "Section");
    model.result().report("rpt1").feature("sec2").feature("sec5").set("source", "firstchild");
    model.result().report("rpt1").feature("sec2").feature("sec5").set("heading", "Mesh 1");
    model.result().report("rpt1").feature("sec2").feature("sec5").create("mesh1", "Mesh");
    model.result().report("rpt1").feature("sec2").feature("sec5").feature("mesh1").set("noderef", "mesh1");
    model.result().report("rpt1").create("sec3", "Section");
    model.result().report("rpt1").feature("sec3").set("source", "custom");
    model.result().report("rpt1").feature("sec3").set("heading", "Study 1");
    model.result().report("rpt1").feature("sec3").create("std1", "Study");
    model.result().report("rpt1").feature("sec3").feature("std1").set("noderef", "std1");
    model.result().report("rpt1").feature("sec3").create("sec1", "Section");
    model.result().report("rpt1").feature("sec3").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec3").feature("sec1").set("heading", "Solver Configurations");
    model.result().report("rpt1").feature("sec3").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").set("source", "firstchild");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").set("heading", "Solution 1");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").create("sol1", "Solver");
    model.result().report("rpt1").feature("sec3").feature("sec1").feature("sec1").feature("sol1")
         .set("noderef", "sol1");
    model.result().report("rpt1").create("sec4", "Section");
    model.result().report("rpt1").feature("sec4").set("source", "custom");
    model.result().report("rpt1").feature("sec4").set("heading", "Results");
    model.result().report("rpt1").feature("sec4").create("sec1", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec1").set("source", "custom");
    model.result().report("rpt1").feature("sec4").feature("sec1").set("heading", "Datasets");
    model.result().report("rpt1").feature("sec4").feature("sec1").create("sec1", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1")
         .set("heading", "Study 1/Solution 1");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1").create("dset1", "DataSet");
    model.result().report("rpt1").feature("sec4").feature("sec1").feature("sec1").feature("dset1")
         .set("noderef", "dset1");
    model.result().report("rpt1").feature("sec4").create("sec2", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").set("source", "custom");
    model.result().report("rpt1").feature("sec4").feature("sec2").set("heading", "Plot Groups");
    model.result().report("rpt1").feature("sec4").feature("sec2").create("sec1", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1")
         .set("heading", "Concentration (tds)");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1").create("pg1", "PlotGroup");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec1").feature("pg1")
         .set("noderef", "pg1");
    model.result().report("rpt1").feature("sec4").feature("sec2").create("sec2", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2")
         .set("heading", "Concentration species in reactor");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2").create("pg1", "PlotGroup");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec2").feature("pg1")
         .set("noderef", "pg2");
    model.result().report("rpt1").feature("sec4").feature("sec2").create("sec3", "Section");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3").set("source", "firstchild");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3")
         .set("heading", " Concentration reacting species along active surface");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3").create("pg1", "PlotGroup");
    model.result().report("rpt1").feature("sec4").feature("sec2").feature("sec3").feature("pg1")
         .set("noderef", "pg3");

    model.sol("sol1").runAll();

    model.result().report("rpt1").run();
    model.result().report("rpt1").run();

    return model;
  }

  public static void main(String[] args) {
    Model model = run();
    run2(model);
  }

}
