package de.bot;

import jakarta.persistence.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MyListener extends ListenerAdapter {

    /**
     * removes the color of a users name by removing the reaction in channel "farbe"
     * @param event
     */
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event){
        Member member = event.retrieveMember().complete();
        Guild guild = event.getGuild();
        try {
            colorRoleRemover(event, member, guild);
        }catch (NullPointerException n){
            System.out.println("Member is null");
        }
    }

    /**
     *
     * @param event
     * @param member
     * @param guild
     */
    private static void colorRoleRemover(MessageReactionRemoveEvent event, Member member, Guild guild) {
        if(event.getChannel().getName().equals("farbe")){
            String farbe = event.getReaction().getEmoji().getName();
            switch(farbe){
                case "\uD83D\uDD34": guild.removeRoleFromMember(member, guild.getRoleById("820345912207081473")).queue();
                    break;
                case "\uD83D\uDFE1": guild.removeRoleFromMember(member, guild.getRoleById("820345981736058881")).queue();
                    break;
                case "\uD83D\uDD35": guild.removeRoleFromMember(member, guild.getRoleById("820345791243485194")).queue();
                    break;
                case "\uD83D\uDFE2": guild.removeRoleFromMember(member, guild.getRoleById("820345860008181811")).queue();
                    break;
                case "\uD83D\uDFE3": guild.removeRoleFromMember(member, guild.getRoleById("820346043132018770")).queue();
                    break;

            }
        }
    }

    /**
     * adds the color of a users name by adding the reaction in channel "farbe"
     * @param event
     */
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        User user = event.getUser();
        Guild guild = event.getGuild();
        Member member = event.retrieveMember().complete();
        try {
            if (hasColorRoles(guild, member)){
                return;
            }

            String farbe = event.getReaction().getEmoji().getName();
            System.out.println(event.getReaction().getEmoji().asUnicode());
            colorRoleAdder(event, user, guild, farbe);
        }
        catch (NullPointerException n){
            System.out.println("Member is null");
        }

    }

    /**
     *
     * @param event
     * @param user
     * @param guild
     * @param farbe
     */
    private static void colorRoleAdder(MessageReactionAddEvent event, User user, Guild guild, String farbe) {
        if(event.getChannel().getName().equals("farbe")){
            switch(farbe){
                case "\uD83D\uDD34": guild.addRoleToMember(user, guild.getRoleById("820345912207081473")).queue();
                    break;
                case "\uD83D\uDFE1": guild.addRoleToMember(user, guild.getRoleById("820345981736058881")).queue();
                    break;
                case "\uD83D\uDD35": guild.addRoleToMember(user, guild.getRoleById("820345791243485194")).queue();
                    break;
                case "\uD83D\uDFE2": guild.addRoleToMember(user, guild.getRoleById("820345860008181811")).queue();
                    break;
                case "\uD83D\uDFE3": guild.addRoleToMember(user, guild.getRoleById("820346043132018770")).queue();
                    break;

            }
        }
    }


    /**
     * checks if a user has already a color role
     * @param guild
     * @param member
     * @return
     */
    private static boolean hasColorRoles(Guild guild, Member member) {
        List<String> roleList = new ArrayList<>();
        for(int i = 0; i< member.getRoles().size(); i++){
            roleList.add(member.getRoles().get(i).getName());
        }
        List<String> colorRoleList = new ArrayList<>();
        colorRoleList.add(guild.getRoleById("820345912207081473").getName());
        colorRoleList.add( guild.getRoleById("820345981736058881").getName());
        colorRoleList.add(guild.getRoleById("820345791243485194").getName());
        colorRoleList.add(guild.getRoleById("820345860008181811").getName());
        colorRoleList.add(guild.getRoleById("820346043132018770").getName());

        for(int i = 0; i<colorRoleList.size(); i++){
            for(int j = 0; j< member.getRoles().size(); j++){
                if(colorRoleList.get(i).equals(member.getRoles().get(j).getName())){
                    guild.getTextChannelById("823599206136545351").sendMessage("Du kannst nur eine Farbe haben!").queue();
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * bot answers on commands
     * @param event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        Message message = event.getMessage();
        String content = message.getContentRaw();
        User user = event.getAuthor();
        imageOrVideoReact(event, message);
        greeting(event, content, user);
        getSelectionSortSteps(event,content);
        getInsertionSortSteps(event, content);
        getBubbleSortSteps(event,content);
        getMergeSortSteps(event, content);
        randomNumberGenerator(event, content);
        getCommandList(event, content);
        addMemberToMemberList(event, content);
        deleteMemberOnList(event, content);
        getMembersList(event, content);

    }

    /**
     * bot sends a list of members who added themselves to database
     * @param event
     * @param content
     */
    private void getMembersList(MessageReceivedEvent event, String content) {
        if(content.startsWith("!membersList")){
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = factory.createEntityManager();
            String jplq = "SELECT member FROM Member member";
            TypedQuery<entity.Member> query = entityManager.createQuery(jplq, entity.Member.class);
            List<entity.Member> members = query.getResultList();
            event.getChannel().sendMessage(" ID |  Name  |  Age  ").queue();
            for(int i = 0; i < members.size() ; i++){
                event.getChannel().sendMessage(members.get(i).getIdmember() + "  |  " + members.get(i).getName() +  "  |  " + members.get(i).getAge()).queue();
            }
            entityManager.close();
            factory.close();
        }
    }

    /**
     * removes a member from the database if he is already on list
     * @param event
     * @param content
     */
    private void deleteMemberOnList(MessageReceivedEvent event, String content) {
        String memberName = event.getAuthor().getName();
        if(content.startsWith("!delete")){
            if(!isMemberInList(memberName)){
                event.getChannel().sendMessage("Du bist nicht auf der Liste!").queue();
            }else{
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
                EntityManager entityManager = factory.createEntityManager();
                String jplq = "SELECT member FROM Member member WHERE member.name = :name";
                TypedQuery<entity.Member> query = entityManager.createQuery(jplq, entity.Member.class);
                query.setParameter("name", memberName);
                List<entity.Member> members = query.getResultList();
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.remove(members.get(0));
                transaction.commit();
                entityManager.close();
                factory.close();
                event.getChannel().sendMessage("Du wurdest entfernt!").queue();
            }
        }
    }

    /**
     * add a member to database member if he is not on list already
     * @param event
     * @param content
     */
    private void addMemberToMemberList(MessageReceivedEvent event, String content) {
        List<Integer> age =  Algorithm.getStringAsList(content);
        if(content.startsWith("!add")){
            if(age.isEmpty()){
                event.getChannel().sendMessage("Ich brauche dein Alter!");
                return;
            }
            if(isMemberInList(event.getAuthor().getName())){
                event.getChannel().sendMessage("Du bist schon auf der Liste!").queue();
            }else {
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
                EntityManager entityManager = factory.createEntityManager();
                entity.Member member = new entity.Member();
                member.setName(event.getAuthor().getName());
                member.setAge(age.get(0));
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(member);
                transaction.commit();
                entityManager.close();
                factory.close();
                event.getChannel().sendMessage("Du wurdest hinzugefügt!").queue();
            }

        }
    }

    /**
     *
     * @param memberName
     * @return true if a member is in the database
     */
    private static boolean isMemberInList(String memberName){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = factory.createEntityManager();
        String jplq = "SELECT name FROM Member WHERE name = :name";
        TypedQuery<entity.Member> query = entityManager.createQuery(jplq, entity.Member.class);
        query.setParameter("name", memberName);
        List<entity.Member> members = query.getResultList();
        entityManager.close();
        factory.close();
        if (!members.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
    /**
     * bot describes his skills
     * @param event
     * @param content
     */
    private static void getCommandList(MessageReceivedEvent event, String content) {
        if(content.startsWith("!commands")){
            event.getChannel().sendMessage("!roll -> zufällige Zahl zwischen 1 und 100 \n " +
                    "!selectionsort ´liste´ -> sortiert und zeigt die Schritte der Sortierung für die eingegebene Liste \n" +
                    "!insertionsort ´liste´ -> sortiert und zeigt die Schritte der Sortierung für die eingegebene Liste \n" +
                    "!bubblesort ´liste´ -> sortiert und zeigt die Schritte der Sortierung für die eingegebene Liste \n" +
                    "!mergesort ´liste´ -> sortiert und zeigt die merge Schritte der Sortierung für die eingegebene Liste \n" +
                    "!add ´age´ -> fügt dich auf die exklusive Member Liste hinzu  \n" +
                    "!remove -> enfernt dich von der exklusiven Member Liste  \n" +
                    "!memberList -> zeigt die exklusive Member Liste  \n" +
                    "Desweiteren kann ich auf manche Bilder und " +
                    "Videos reagieren, wenn ich anhand des Titels etwas lustiges oder trauriges erkennen und natürlich auch Leute begrüßen. " +
                    "Für die Rollenverteilung im Channel Farbe bin ich auch verantwortlich. Gelegentlich grüße ich auch Leute. ").queue();

        }
    }

    /**
     * bot reacts on images and videos
     * @param event
     * @param message
     */
    private static void imageOrVideoReact(MessageReceivedEvent event, Message message) {
        List<Message.Attachment> messageAttachments = message.getAttachments();
        if(!messageAttachments.isEmpty()) {
            Message.Attachment messageAttachment = messageAttachments.get(0);
            if (messageAttachment.isImage()|| messageAttachment.isVideo()) {
                String url = messageAttachment.getProxyUrl();
                if(url.toLowerCase().contains("meme") || url.toLowerCase().contains("joke")){
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("HAHAHAHA").queue();

                }
                else if(url.toLowerCase().contains("sad") || url.toLowerCase().contains("bad")){
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage(":(").queue();
                }else{
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("I dont know").queue();
                }

            }
        }
    }

    /**
     * Generates a random number from 1-100
     * @param event
     * @param content
     */
    private static void randomNumberGenerator(MessageReceivedEvent event, String content) {
        if(content.startsWith("!roll")){
            int randomNumber = (int) (Math.random()* 100) + 1;
            String randomNumberInString = String.valueOf(randomNumber);
            MessageChannel channel = event.getChannel();
            channel.sendMessage(randomNumberInString).queue();

        }
    }

    /**
     * Bot greets new member if they write Hi,Hello,Hey,...
     * @param event
     * @param content
     * @param user
     */
    private static void greeting(MessageReceivedEvent event, String content, User user) {
        if(event.getChannel().getName().equals("kertbot") && content.startsWith("H")){
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Hallo "+ user.getName().toString()).queue();
        }
    }

    /**
     * shows each step in a selection sort algorithm for a list
     * @param event
     * @param content
     */
    private static void getSelectionSortSteps(MessageReceivedEvent event, String content){
        if(content.startsWith("!selectionsort")){
            List<Integer> list = Algorithm.getStringAsList(content);
            MessageChannel channel = event.getChannel();
            if(!list.isEmpty()){
                int lowestNumber = 0;
                int position = 0;
                channel.sendMessage("Das ist die zu sortierende Liste: " + Algorithm.getListAsString(list)).queue();
                for(int i = 0; i < list.size(); i++){
                    lowestNumber = list.get(i);
                    position = i;
                    for(int j = i; j < list.size(); j++) {
                        if(lowestNumber > list.get(j)){
                            lowestNumber = list.get(j);
                            position = j;
                        }
                    }
                    Algorithm.changeNumbers(list, position, i);
                    channel.sendMessage((i+1) +". Durchlauf: "+Algorithm.getListAsString(list)).queue();
                }
                channel.sendMessage("Die Liste ist jetzt sortiert.").queue();
            }else{
                channel.sendMessage("Liste ist leer").queue();
            }
        }

    }
    /**
     * shows each step in an insertion sort algorithm for a list
     * @param event
     * @param content
     */
    private static void getInsertionSortSteps(MessageReceivedEvent event, String content){
        if(content.startsWith("!insertionsort")){
            List<Integer> list = Algorithm.getStringAsList(content);
            MessageChannel channel = event.getChannel();
            if(!list.isEmpty()){
                List<Integer> newList = new LinkedList<>();
                int compareNumber = 0;
                int position = 0;
                channel.sendMessage("Das ist die zu sortierende Liste: " + Algorithm.getListAsString(list)).queue();
                newList.add(list.get(0));
                for(int i = 1; i<list.size(); i++){
                    compareNumber = list.get(i);
                    position = 0;
                    for(int j=0; j< newList.size(); j++){
                        if(compareNumber>newList.get(j)){
                            position = j+1;
                        }else{
                            break;
                        }
                    }
                    newList.add(position, compareNumber);
                    channel.sendMessage(i +". Durchlauf: " + Algorithm.getListAsString(newList)).queue();
                }
                channel.sendMessage("Die Liste ist jetzt sortiert.").queue();
            }else{
                channel.sendMessage("Liste ist leer").queue();
            }
        }
    }

    /**
     * shows each step in a bubble sort algorithm for a list
     * @param event
     * @param content
     */
    private static void getBubbleSortSteps(MessageReceivedEvent event, String content){
        if(content.startsWith("!bubblesort")){
            List<Integer> list = Algorithm.getStringAsList(content);
            MessageChannel channel = event.getChannel();
            if(!list.isEmpty()){
                boolean newChange;
                int counter = 0;
                for(int i = list.size()-1; i >0 ; i--){
                    newChange = false;
                    for(int j = 0 ; j< i; j++ ){
                        if(list.get(j)> list.get(j+1)){
                            Algorithm.changeNumbers(list, j, j+1);
                            newChange = true;
                        }

                    }
                    counter++;
                    channel.sendMessage(counter +". Durchlauf: "+Algorithm.getListAsString(list)).queue();
                    if(!newChange){
                        break;
                    }
                }
                channel.sendMessage("Die Liste ist jetzt sortiert.").queue();

            }else{
                channel.sendMessage("Liste ist leer").queue();
            }
        }

    }

    /**
     * shows merge steps for merge sort algorithm
     * @param event
     * @param content
     */
    private static void getMergeSortSteps(MessageReceivedEvent event, String content){
        if(content.startsWith("!mergesort")){
            List<Integer> list = Algorithm.getStringAsList(content);
            MessageChannel channel = event.getChannel();
            if(!list.isEmpty()){
                channel.sendMessage("Das ist die zu sortierende Liste: " + Algorithm.getListAsString(list)).queue();
                List<Integer> sortedlist = mergeSort(list, channel);
                channel.sendMessage("Das ist die sortierte Liste: " + Algorithm.getListAsString(sortedlist)).queue();
            }else{
                channel.sendMessage("Liste ist leer").queue();
            }
        }

    }

    /**
     *
     * @param list
     * @param channel
     * @return the sorted list
     */

    private static List<Integer> mergeSort(List<Integer> list, MessageChannel channel){
        List<Integer> showList = new LinkedList<>();
        if(list.size() <= 1){
            return list;
        }else{
            int middle = list.size()/2;
            List<Integer> leftList = list.subList(0, middle);
            List<Integer> rightList = list.subList(middle , list.size());
            leftList = mergeSort(leftList, channel);
            rightList = mergeSort(rightList, channel);
            channel.sendMessage(Algorithm.getListAsString(Algorithm.merge(leftList, rightList))).queue();
            return Algorithm.merge(leftList, rightList);
        }
    }

}
