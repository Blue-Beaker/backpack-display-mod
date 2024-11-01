package io.bluebeaker.backpackdisplay.displayslot;
import io.bluebeaker.backpackdisplay.utils.NBTUtils;
import io.bluebeaker.backpackdisplay.utils.ValueOperator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;

public class ValueOperation {
    public final ValueOperator operator;
    public final String[] pathToValue;
    /**
     * @param operation String starting with one of + - * / ^ %, followed by a nbt path string
     */
    public ValueOperation(String path){
        this.pathToValue = NBTUtils.getKeysList(path.substring(1));
        switch (path.charAt(0)) {
            case '+':
                this.operator=new ValueOperator.ADD();break;
            case '-':
                this.operator=new ValueOperator.SUB();break;
            case '*':
                this.operator=new ValueOperator.MUL();break;
            case '/':
                this.operator=new ValueOperator.DIV();break;
            case '^':
                this.operator=new ValueOperator.POW();break;
            case '%':
                this.operator=new ValueOperator.MOD();break;
            default:
                this.operator=null;break;
        }
    }
    public int doOperation(int num,NBTTagCompound tag){
        if(this.operator==null){
            return -1;
        }
        NBTBase tagNum = NBTUtils.getTagRecursive(tag, pathToValue);
        if(NBTUtils.isNumber(tagNum))
        return this.operator.calc(num, ((NBTPrimitive)tagNum).getInt());
        else return -1;
    }
}
