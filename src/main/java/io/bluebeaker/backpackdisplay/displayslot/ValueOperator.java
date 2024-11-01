package io.bluebeaker.backpackdisplay.displayslot;

public interface ValueOperator {
    public int calc(int a,int b);
    public static class ADD implements ValueOperator{
        @Override
        public int calc(int a, int b) {
            return a+b;
        }
    }
    public class SUB implements ValueOperator{
        @Override
        public int calc(int a, int b) {
            return a-b;
        }
    }
    public class MUL implements ValueOperator{
        @Override
        public int calc(int a, int b) {
            return a*b;
        }
    }
    public class DIV implements ValueOperator{
        @Override
        public int calc(int a, int b) {
            return a/b;
        }
    }
    public class POW implements ValueOperator{
        @Override
        public int calc(int a, int b) {
            int result=1;
            for(int i=0;i<b;i++){
                result=result*a;
            }
            return result;
        }
    }
    public class MOD implements ValueOperator{
        @Override
        public int calc(int a, int b) {
            return a%b;
        }
    }
}
