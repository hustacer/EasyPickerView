package com.hzn.easypickerview;

import java.util.List;

public class Translation {
    private boolean success;
    private String terms;
    private String privacy;
    private Long timestamp;
    private String source;
    private quotesBean quotes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public quotesBean getQuotes() {
        return quotes;
    }

    public void setQuotes(quotesBean quotes) {
        this.quotes = quotes;
    }

    public static class quotesBean {
        private List<String> USD;

        public List<String> getUSD() {
            return USD;
        }

        public void setUSD(List<String> USD) {
            this.USD = USD;
        }
    }
}
