package io.rajatchaudhary.covidtracker;

import io.rajatchaudhary.covidtracker.models.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
        // need for CovidDataService to be spring service springg service that will execute te method fetch
        // add stereotype service to mke it a spring service will create instance
@Service
public class CovidDataService {

    private static String Virus_Data_Url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    /// this list will get updated with new stats every other day

    private List<Location> completeData = new ArrayList<>();
    ///it is going to make https call to url
    ///will use http client will execute after class instance is created
    //schedule run of a method as user wanted it fixedrate interval will run every second


            public List<Location> getCompleteData() {
                return completeData;
            }

            @PostConstruct
            @Scheduled(cron = "* * 1 * * *" )
    public void fetchVirusData() throws IOException, InterruptedException {

                ///to deal with concurrency issues we will first fill this list
        List<Location> new_completeData = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(Virus_Data_Url)).build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());

        /// httpResponse can be parsed using commons-csv libray
                //instance of reader that parses string
                StringReader csvBodyReader = new StringReader(httpResponse.body()) ;
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
                for (CSVRecord record : records) {
                    Location location = new Location();
                    location.setStates(record.get("Province/State"));
                    location.setCountry(record.get("Country/Region"));
                    ///the list get updated every other day so we have to access the last column of the file
                    ///file will give data in string format need to parseit to integer
                    int latestCases = Integer.parseInt(record.get(record.size()-1));
                    int previousCases = Integer.parseInt(record.get(record.size()-1));

                    location.setUpdatedCases(latestCases);
                    location.setCasesSincePreviousDay(latestCases-previousCases);


                    new_completeData.add(location);
                }

                this.completeData = new_completeData;
    }
}
