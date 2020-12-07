package com.example.spaceinvaders;

public class Cooldown {
    long cooldown;
    long time;
    public Cooldown(long time_ms)
    {
        cooldown=time_ms;
        time=0;
    }

    public void update(long elapsed)
    {
        time=time+elapsed;
    }

    public void reset()
    {
        time=0;
    }

    public boolean readyReset()
    {
        boolean ready=this.ready();
        if(ready)
        {
            this.reset();
        }
        return ready;
    }

   public boolean ready(){
        return time>=cooldown;
   }
}
