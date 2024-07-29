package BackendCode;

import java.io.Serializable;

/**
 *
 * @author gallo
 */
public class TimeInterval implements Serializable {
    private static final long serialVersionUID = 1L; // Adiciona um serialVersionUID para garantir a compatibilidade de serialização
    private static final long MILLISECONDS_IN_HOUR = 1000 * 60 * 60;

    private long rentTime;
    private long returnTime;

    public TimeInterval(long rentTime, long returnTime) {
        this.rentTime = rentTime;
        this.returnTime = returnTime;
    }

    public long getRentTime() {
        return rentTime;
    }

    public void setRentTime(long rentTime) {
        this.rentTime = rentTime;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public long getTotalHours() {
        return (returnTime - rentTime) / MILLISECONDS_IN_HOUR;
    }

    @Override
    public String toString() {
        return "TimeInterval{" + "rentTime=" + rentTime + ", returnTime=" + returnTime + '}';
    }
}
