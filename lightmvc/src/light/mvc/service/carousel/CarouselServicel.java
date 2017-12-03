package light.mvc.service.carousel;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.carousel.Carousel;

public interface CarouselServicel {
	public List<Carousel> dataGrid(Carousel carousel, PageFilter ph);

	public Long count(Carousel carousel, PageFilter ph);

	public void add(Carousel carousel);
	
	public void edit(Carousel carousel);
	
	public Carousel get(Long id);

}
