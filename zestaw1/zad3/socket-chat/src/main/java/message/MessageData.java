package message;

import com.google.common.base.Preconditions;
import org.joda.time.LocalDateTime;

import java.io.Serializable;

/**
 * Created by novy on 12.03.15.
 */
class MessageData implements Serializable {

    private final String nick;
    private final String content;
    private final LocalDateTime dateTime;

    public MessageData(String nick, String content, LocalDateTime dateTime) {
        Preconditions.checkNotNull(nick);
        Preconditions.checkNotNull(content);
        Preconditions.checkNotNull(dateTime);

        Preconditions.checkArgument(!nick.isEmpty(), "Nick cannot be empty!");
        Preconditions.checkArgument(nick.length() <= 6, "Cannot create message with nick longer than 6 characters");
        Preconditions.checkArgument(content.length() <= 20, "Cannot create message with content longer than 20 characters");

        this.nick = nick;
        this.content = content;
        this.dateTime = dateTime;
    }

    public MessageData(String nick, String content) {
        this(nick, content, LocalDateTime.now());
    }

    public String nick() {
        return nick;
    }

    public String content() {
        return content;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageData that = (MessageData) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (nick != null ? !nick.equals(that.nick) : that.nick != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nick != null ? nick.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}

