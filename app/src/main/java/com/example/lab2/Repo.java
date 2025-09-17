package com.example.lab2;

import com.example.lab2.Model.AccessPoint;
import com.example.lab2.Model.Router;
import com.example.lab2.Model.Switch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Repo {
    private static Repo INSTANCE;
    private final List<Router> routers = new ArrayList<>();

    private Repo(){}

    public static synchronized Repo getInstance(){
        if (INSTANCE == null) INSTANCE = new Repo();
        return INSTANCE;
    }

    public List<Router> getRouters(){ return routers; }

    public Router getRouterById(String id){
        for (Router r : routers) if (r.id.equals(id)) return r;
        return null;
    }

    public void addRouter(Router r){ routers.add(r); }

    public void updateRouter(Router updated){
        for (int i=0; i<routers.size(); i++){
            if (routers.get(i).id.equals(updated.id)){ routers.set(i, updated); return; }
        }
    }

    public void deleteRouter(String id){
        Iterator<Router> it = routers.iterator();
        while (it.hasNext()){
            if (it.next().id.equals(id)){ it.remove(); return; }
        }
    }

    //SWITCH

    private final java.util.List<Switch> switches = new java.util.ArrayList<>();

    public java.util.List<Switch> getSwitches() { return switches; }

    public Switch getSwitchById(String id) {
        for (Switch s : switches) if (s.id.equals(id)) return s;
        return null;
    }

    public void addSwitch(Switch s) { switches.add(s); }

    public void updateSwitch(Switch updated) {
        for (int i = 0; i < switches.size(); i++) {
            if (switches.get(i).id.equals(updated.id)) { switches.set(i, updated); return; }
        }
    }

    public void deleteSwitch(String id) {
        java.util.Iterator<Switch> it = switches.iterator();
        while (it.hasNext()) if (it.next().id.equals(id)) { it.remove(); return; }
    }

    //Access point
    private final java.util.List<AccessPoint> aps = new java.util.ArrayList<>();

    public java.util.List<AccessPoint> getAps() { return aps; }

    public AccessPoint getApById(String id) {
        for (AccessPoint a : aps) if (a.id.equals(id)) return a;
        return null;
    }

    public void addAp(AccessPoint ap) { aps.add(ap); }

    public void updateAp(AccessPoint updated) {
        for (int i = 0; i < aps.size(); i++) {
            if (aps.get(i).id.equals(updated.id)) { aps.set(i, updated); return; }
        }
    }

    public void deleteAp(String id) {
        java.util.Iterator<AccessPoint> it = aps.iterator();
        while (it.hasNext()) if (it.next().id.equals(id)) { it.remove(); return; }
    }


}
