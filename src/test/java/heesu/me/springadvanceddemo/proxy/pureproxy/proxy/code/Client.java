package heesu.me.springadvanceddemo.proxy.pureproxy.proxy.code;

public class Client {

    private Subject subject;

    public Client(Subject subject) {
        this.subject = subject;
    }

    public void execute(){
        subject.operation();
    }
}
