package com.satyasnehith.acrud.core.database

import com.satyasnehith.acrud.core.model.Article

object FakeData {
    private val articlePairs = listOf(
        Pair("What is Lorem Ipsum?",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."),
        Pair("Why do we use it?",
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."),
        Pair("Where does it come from?",
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."),
        Pair("Where does it come from? -2",
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham."),
        Pair("Where can I get some?",
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."),
        Pair("What happens if I don’t write a meta description? Why is it important?\n",
                "If there’s no meta description set for a page, search engines such as Google or Bing will typically generate one from the page content. This can sometimes happen anyway, so be aware that your meta description is not always what displays beneath the search result if Google, Bing, or whoever has decided that some content on the page would be a better fit. This is not something that you can control (so you can’t force Google or Bing to display your meta description as opposed to their own).\n" +
                "\n" +
                "However, the better your meta description and the more suited it is to the types of queries that might lead a searcher to that page, the more likely it is that your own text will display. And if you don’t have a meta description set, sometimes totally inappropriate text can get pulled in – like a cookie consent pop-up (I’ve seen it happen). Which means it’s far better to have one than to not.\n" +
                "\n" +
                "So, that’s the theory on how to write a good meta description – but what does it look like in practice? Here are 33 examples of well-written meta descriptions from a variety of brands and companies, with some analysis as to why they’re effective."),
        Pair("What is the optimal meta description length?\n",
                "When Google increased the meta description character limit to 320 back in December 2017, SEOs got a little too excited and started re-writing meta descriptions for their sites. So it’s no surprise many started freaking out in May 2018 when Google unexpectedly dropped the meta description length back to 160 characters.\n" +
                "\n" +
                "The fact is, Google has never explicitly stated what the meta description length should be, even when they’ve made sweeping changes to search pages. In fact, Google’s public liaison of search, Danny Sullivan, advised against rewriting descriptions.\n" +
                "\n" +
                "\n" +
                "As Google Webmaster Trends Analyst John Mueller pointed out on Twitter, search is in a constant state of flux – and SEOs are along for the ride:")
        )


    val articles = List(articlePairs.size) { index ->
        val articlePair = articlePairs[index]
        Article(
            id = index + 1,
            title = articlePair.first,
            body = articlePair.second
        )
    }

}

