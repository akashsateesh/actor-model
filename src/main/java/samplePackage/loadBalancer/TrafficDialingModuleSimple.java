package samplePackage.loadBalancer;

public class TrafficDialingModuleSimple {

    private volatile int r, idm,count;
    private volatile double rpasWeightNative;

    public TrafficDialingModuleSimple(){
        r = idm = 0;
        count = 1;
        rpasWeightNative = 0.0;
    }

    public synchronized Service calculate(double rpasWeight){

        if(rpasWeight != rpasWeightNative){
            rpasWeightNative = rpasWeight;
            r = idm = 0;
            count = 1;
        }

        Service service = Service.IDM;

        double weightRatio = (double)(r+1)/(double)(count);

        if(weightRatio <= rpasWeightNative) {
            r++;
            service = Service.RPAS;
        }
        else {
            idm++;
        }

        count ++;

        if(count == 100){
            r = idm = 0;
            count = 1;
        }

        return service;
    }

}
