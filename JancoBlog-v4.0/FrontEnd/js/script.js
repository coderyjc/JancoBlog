const headerEl = document.querySelector("header")

const scrollTopEl = document.querySelector(".scrollToTop")

window.addEventListener("scroll", ()=>{

  if(window.scrollY > 400){
    if(!headerEl.classList.contains("sticky")){
      headerEl.classList.toggle("sticky")
    }
    scrollToTop.style.display = "block"
  }else if (window.scrollY == 0){
    if(headerEl.classList.contains("sticky")){
      headerEl.classList.remove("sticky")  
    }
    scrollToTop.style.display = "none"
  }

  if(headerEl.classList.contains("open")){
    headerEl.classList.remove("open");
  }

})