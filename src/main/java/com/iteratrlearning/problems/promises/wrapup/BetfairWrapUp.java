package com.iteratrlearning.problems.promises.wrapup;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BetfairWrapUp {

    private Dictionary dictionary = new Dictionary();

    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();
        String url = "http://www.betfair.jobs/search-results/";

        new BetfairWrapUp().sequentialFetch(url);
        // TODO: implement concurrentFetch
        new BetfairWrapUp().concurrentFetch(url);


        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private void sequentialFetch(String url) throws IOException {
        String content = getContentAsString(url);

        List<JobMatch> matches = getFirstMatches(content, 8);

        for(JobMatch jobMatch : matches) {
            String jobPage = getContentAsString(jobMatch.getJobUrl().getAddress());
            Keywords words = dictionary.getMostFrequentWords(jobPage, 10);

            printJobInfo(new JobInfo(jobMatch.getJobTitle(), words));
        }
    }

    private void concurrentFetch(String url) {

        // TODO:
        // 1) Fetch the content of http://www.betfair.jobs/search-results using CompletableFuture
        /** HINT: wrap up the call to {@link #getContentAsString} into a CompletableFuture using supplyAsync
            or you may wish to refactor it to return CompletableFuture<String> */

        // 2) Extract the first 8 JobTitle and JobURL
        /** HINT: use {@link #getFirstMatches} */

        // 3) for each JobURL fetch the content
        // 4) for each content calculate the most frequent keywords
        /** HINT: use {@link Dictionary#getMostFrequentWords} */

        // 5) aggregate information into a JobInfo object
        // 6) print information
        /** HINT: use {@link #printJobInfo(JobInfo)} */

    }

    private void printJobInfo(JobInfo jobInfo) {
        System.out.println(jobInfo.getJobTitle().getName() + ": " + jobInfo.getKeywords().getWords());
    }

    /**
     * Extract a list of job matches given raw html content of a the betfair jobs page
     * @param content the page to parse
     * @param n the first number of pairs of job titles and url to extract
     * @return a list of job match which includes the job title and job url for the full specification
     */
    private List<JobMatch> getFirstMatches(String content, int n) {
        String pattern = "<h3><a href=\"(.+?)\">(.+?)</a></h3>";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(content);

        List<JobMatch> result = new ArrayList<>();
        int  count = 0;
        while(matcher.find() && count < n) {
            String page = matcher.group(1);
            String name = matcher.group(2);
            result.add(new JobMatch(new JobTitle(name), new JobURL(page)));
            count++;
        }
        return result;
    }

    public <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allFuturesDone =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allFuturesDone.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(toList()));
    }

    /**
     * Fetches the raw html content for a page given its url
     * @param url the url to fetch raw html for
     * @return a string containing raw html of a url
     */
    private String getContentAsString(String url)
    {
        try {
            return Request
                    .Get(url)
                    .execute()
                    .returnContent()
                    .asString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    static public class JobMatch {
        private JobTitle jobTitle;
        private JobURL jobUrl;

        public JobMatch(JobTitle jobTitle, JobURL jobUrl) {
            this.jobTitle = jobTitle;
            this.jobUrl = jobUrl;
        }

        public JobTitle getJobTitle() {
            return jobTitle;
        }

        public JobURL getJobUrl() {
            return jobUrl;
        }
    }


    static public class JobInfo {
        private JobTitle jobTitle;
        private Keywords keywords;

        public JobInfo(JobTitle jobTitle, Keywords keywords) {
            this.jobTitle = jobTitle;
            this.keywords = keywords;
        }

        public JobTitle getJobTitle() {
            return jobTitle;
        }

        public Keywords getKeywords() {
            return keywords;
        }

        @Override
        public String toString() {
            return "JobInfo{" +
                    "jobTitle=" + jobTitle +
                    ", keywords=" + keywords +
                    '}';
        }
    }

    static public class Keywords {
        private List<String> words;

        public Keywords(List<String> words) {
            this.words = words;
        }

        public List<String> getWords() {
            return words;
        }

        @Override
        public String toString() {
            return "Keywords{" +
                    "words=" + words +
                    '}';
        }
    }

    static public class JobTitle {

        private String name;

        public JobTitle(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "JobTitle{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static public class JobURL {
        private String address;

        public JobURL(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        @Override
        public String toString() {
            return "JobURL{" +
                    "address='" + address + '\'' +
                    '}';
        }
    }

    static public class Dictionary {

        static String[] stopWordsArray = {"a", "as", "able", "about", "above", "according", "accordingly", "across",
                "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
        static String[] randomWordsArray = {"li", "ul", "p", "div", "solid", "paddy", "power", "betfair", "screen",
                "medium"};
        static Set<String> stopWordsSet = new HashSet<>(Arrays.asList(stopWordsArray));
        static Set<String> randomWordsSet = new HashSet<>(Arrays.asList(randomWordsArray));

        /**
         * Returns a list of useful keywords given a string by filtering out stop words and words that are not relevant
         * @param input the string to extract useful keywords
         * @param n the number of keywords to return
         * @return the relevant keywords
         */
        private Keywords getMostFrequentWords(String input, int n) {
            Map<String, Long> occurrences
                    = Arrays.stream(input.split("\\s+"))
                    .filter(word -> word.matches("\\p{Alpha}+"))
                    .map(String::toLowerCase)
                    .filter(this::isRelevant)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<String> strings = occurrences.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Entry<String, Long>::getValue).reversed())
                    .limit(n)
                    .map(Entry::getKey)
                    .collect(Collectors.toList());

            return new Keywords(strings);
        }

        private boolean isRelevant(String word) {
            return !stopWordsSet.contains(word) && !randomWordsSet.contains(word);
        }
    }



}
