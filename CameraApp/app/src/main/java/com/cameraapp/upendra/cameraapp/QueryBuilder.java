package com.cameraapp.upendra.cameraapp;

/**
 * Created by Upendra on 7/19/2015.
 */
public class QueryBuilder {

    /**
     * Specify your database name here
     * @return
     */
    public String getDatabaseName() {
        return "scrapbook";
    }

    /**
     * Specify your MongoLab API here
     * @return
     */
    public String getApiKey() {
        return "uQjTsh2A9Uwc8exurr3SmLneDMZakVpY";
    }

    /**
     * This constructs the URL that allows you to manage your database,
     * collections and documents
     * @return
     */
    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    /**
     * Completes the formating of your URL and adds your API key at the end
     * @return
     */
    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    /**
     * Returns the mynewcollection collection
     * @return
     */
    public String documentRequest()
    {
        return "mynewcollection";
    }

    /**
     * Builds a complete URL using the methods specified above
     * @return
     */
    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    /**
     * Formats the contact details for MongoHQ Posting
     * @param contact: Details of the person
     * @return
     */
    public String createContact(MyContact contact)
    {
        return String
                .format("{\"document\" : {\"first_name\": \"%s\", "
                                + "\"last_name\": \"%s\", \"email\": \"%s\", "
                                + "\"phone\": \"%s\"}, \"safe\" : true}",
                        contact.first_name, contact.last_name, contact.email, contact.phone);
    }

}
