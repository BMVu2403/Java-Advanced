package PTIT_CNTT1_IT203B_Session08.Exercise04;

public interface Subject {
    void attach(Observer o);

    void detach(Observer o);

    void notifyObservers();
}
