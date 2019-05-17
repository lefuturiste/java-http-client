package fr.lefuturiste.httpclient;

public class StatusLine {
    private String rawStatusLine;
    private String protocol;
    private String statusMessage;
    private int statusCode;
    private boolean isValid = true;

    public StatusLine(String rawStatusLine){
        this.rawStatusLine = rawStatusLine;
        parse();
    }

    private void parse() {
        String[] statusLineComponents = this.rawStatusLine.split(" ");
        if (statusLineComponents.length != 3) {
            this.isValid = false;
        } else {
            this.protocol = statusLineComponents[0];
            this.statusCode = Integer.parseInt(statusLineComponents[1]);
            this.statusMessage = statusLineComponents[2];
        }
    }

    public String getRawStatusLine() {
        return rawStatusLine;
    }

    public boolean isValid() {
        return isValid;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getProtocol() {
        return protocol;
    }
}
