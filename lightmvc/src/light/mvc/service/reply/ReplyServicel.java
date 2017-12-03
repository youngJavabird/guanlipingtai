package light.mvc.service.reply;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.carousel.Carousel;
import light.mvc.pageModel.reply.Reply;

public interface ReplyServicel {
	public List<Reply> dataGrid(Reply reply, PageFilter ph);

	public Long count(Reply reply, PageFilter ph);

	public void add(Reply reply);
	
	public void edit(Reply reply);
	
	public Reply get(Long id);

}
