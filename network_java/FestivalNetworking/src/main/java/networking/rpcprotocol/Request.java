package networking.rpcprotocol;

public class Request implements java.io.Serializable{
    private RequestType type;
    private Object data;

    private Request() {}

    public RequestType getType() {return type;}
    public Object getData() {return data;}

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public static class Builder {
        private Request request = new Request();

        public Builder type(RequestType type) {
            request.setType(type);
            return this;
        }

        public Builder data(Object data) {
            request.setData(data);
            return this;
        }

        public Request build() {
            return request;
        }
    }

    private void setType(RequestType type) {
        this.type = type;
    }
    private void setData(Object data) {
        this.data = data;
    }
}
