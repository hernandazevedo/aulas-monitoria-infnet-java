package by.giava.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.weld.context.http.HttpConversationContext;

public abstract class ConverSationController {

	@Inject Conversation conversation;
	
	public static final long CONVERSATION_TIMEOUT = 3000000;//50 minutos
	public static final long CONVERSATION_CONCURRENT_TIMEOUT = 10000; //10 segundos
	
	@Inject
    private Instance<HttpConversationContext> contextInstance;
	
	@PostConstruct
	public void initConversationContext(){
		//CORRIGINDO O BUG :: org.jboss.weld.context.BusyConversationException: WELD-000322 Conversation lock timed out
		HttpConversationContext conversationContext = contextInstance.get();
		conversationContext.setConcurrentAccessTimeout(CONVERSATION_CONCURRENT_TIMEOUT);
	}
	
	public void endConversation()
	{
		if (!conversation.isTransient())
		{
			conversation.end();
			return;
		}
			
	}
	
	public void beginConversation()
	{
		
		
		if (conversation.isTransient())
		{
			
			conversation.begin();
			conversation.setTimeout(CONVERSATION_TIMEOUT);
			
			return;
		}
	}
	
}
