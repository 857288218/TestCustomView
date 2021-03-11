package BuilderPattern;

/**
 * 建造者模式：为了分离对象的属性与创建过程
 * <p>
 * 建造者模式是构造方法的一种替代方案，为什么需要建造者模式，我们可以想，假设有一个对象里面有20个属性
 * 对开发者来说这不是疯了，也就是说我要去使用这个对象，我得去了解每个属性的含义，然后在构造函数或者Setter中一个一个去指定。
 * 更加复杂的场景是，这些属性之间是有关联的，比如属性1=A，那么属性2只能等于B/C/D，这样对于开发者来说更是增加了学习成本，开源产品这样的一个对象相信不会有太多开发者去使用。
 * <p>
 * 为了解决以上的痛点，建造者模式应运而生，对象中属性多，但是通常重要的只有几个，因此建造者模式会让开发者指定一些比较重要的属性或者让开发者指定某几个对象类型，
 * 然后让建造者去实现复杂的构建对象的过程，这就是对象的属性与创建分离。这样对于开发者而言隐藏了复杂的对象构建细节，降低了学习成本，同时提升了代码的可复用性。
 */

public class Car {

    private String size;            //尺寸
    private String wheel;           //轮胎
    private String steeringWheel;   //方向盘
    private String pedestal;        //底座
    private String displacement;    //排量
    private String maxSpeed;        //最大速度

    private Car(CarBuilder builder) {
        if ("紧凑型车".equals(builder.getType())) {
            this.size = "大小--紧凑型车";
        } else if ("中型车".equals(builder.getType())) {
            this.size = "大小--中型车";
        } else {
            this.size = "大小--其他";
        }

        if ("很舒适".equals(builder.getComfort())) {
            this.steeringWheel = "方向盘--很舒适";
            this.pedestal = "底座--很舒适";
        } else if ("一般舒适".equals(builder.getComfort())) {
            this.steeringWheel = "方向盘--一般舒适";
            this.pedestal = "底座--一般舒适";
        } else {
            this.steeringWheel = "方向盘--其他";
            this.pedestal = "底座--其他";
        }

        if ("动力强劲".equals(builder.getPower())) {
            this.displacement = "排量--动力强劲";
            this.maxSpeed = "最大速度--动力强劲";
            this.steeringWheel = "轮胎--动力强劲";
        } else if ("动力一般".equals(builder.getPower())) {
            this.displacement = "排量--动力一般";
            this.maxSpeed = "最大速度--动力一般";
            this.steeringWheel = "轮胎--动力一般";
        } else {
            this.displacement = "排量--其他";
            this.maxSpeed = "最大速度--其他";
            this.steeringWheel = "轮胎--其他";
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWheel() {
        return wheel;
    }

    public void setWheel(String wheel) {
        this.wheel = wheel;
    }

    public String getSteeringWheel() {
        return steeringWheel;
    }

    public void setSteeringWheel(String steeringWheel) {
        this.steeringWheel = steeringWheel;
    }

    public String getPedestal() {
        return pedestal;
    }

    public void setPedestal(String pedestal) {
        this.pedestal = pedestal;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "size='" + size + '\'' +
                ", wheel='" + wheel + '\'' +
                ", steeringWheel='" + steeringWheel + '\'' +
                ", pedestal='" + pedestal + '\'' +
                ", displacement='" + displacement + '\'' +
                ", maxSpeed='" + maxSpeed + '\'' +
                '}';
    }

    public static class CarBuilder {
        private String type;      //车型
        private String power;     //动力
        private String comfort;   //舒适性

        public Car build() {
            return new Car(this);
        }

        public CarBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public CarBuilder setPower(String power) {
            this.power = power;
            return this;
        }

        public CarBuilder setComfort(String comfort) {
            this.comfort = comfort;
            return this;
        }

        public String getType() {
            return type;
        }

        public String getPower() {
            return power;
        }

        public String getComfort() {
            return comfort;
        }
    }
}
