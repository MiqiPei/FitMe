package com.fitme.community.dao;


import com.fitme.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> selectConversations(int userId, int offset, int limit);

    int selectConversationCount(int userId);

    List<Message> selectLetters(String conversationId, int offset, int limit);

    int selectLetterCount(String conversationId);

    int selectLetterUnreadCount(int userId, String conversationId);

    //new messages
    int insertMessage(Message message);

    // update message status
    int updateStatus(List<Integer> ids, int status);
}
