package frc.robot.systems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import frc.robot.Util;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Output {

    private SparkMax outMotor = new SparkMax(12, MotorType.kBrushless);
    double outPosition;
    double outSpeed = 0.05;
    double inputSpeed = 0.01;
    int inputDuration = 100;
    int timer = 0;
    char cmd;

    Output(){
        cmd = 's';
        SparkMaxConfig outConfig = new SparkMaxConfig();

        outMotor.getEncoder().setPosition(0);

        outConfig.inverted(false);
        outConfig.idleMode(IdleMode.kCoast);
        outMotor.configure(outConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        outPosition = outMotor.getEncoder().getPosition();
    }

    public void update(){
        switch(cmd){
            case 's':
                outMotor.set(0);
            break;
            case 'o':
                outMotor.set(outSpeed);
            break;
            case 'i':
                if (timer <= inputDuration){
                    outMotor.set(-inputSpeed);
                    timer ++;
                }
            break;
        }
    }

    public void outStop(){
        cmd = 's';
    }

    public void outOut(){
        cmd = 'o';
    }

    public void intake(){
        cmd = 'i';
    }
}
