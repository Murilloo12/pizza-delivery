package academy.quarkus.ren;

import java.util.List;

import academy.quarkus.ren.data.SliderItem;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;


public class PizzasCtrl extends Controller{

    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance index (List<SliderItem> sliderItems);
    }

    static List<SliderItem> sliderItems = List.of(
        new SliderItem(
                "Audi A6",
                "We cooked your desired Pizza Recipe",
                "A small river named Duden flows by their place and supplies it with the necessary regelialia",
                "Order Now",
                "View Menu",
                "images/bg_1.png"
        ),
        new SliderItem(
                "Volvo xc 90",
                "Italian Pizza",
                "A small river named Duden flows by their place and supplies it with the necessary regelialia",
                "Order Now",
                "View Menu",
                "images/bg_2.png"
        ),
        new SliderItem(
                "Tesla Model X",
                "Italian Cuizine",
                "A small river named Duden flows by their place and supplies it with the necessary regelialia",
                "Order Now",
                "View Menu",
                "images/bg_3.jpg"
        )
);

    @Path("/")  
    @GET
    public TemplateInstance index () {
        return Templates.index(sliderItems);
    }
}
