import router from '@system.router';
export default {
    data: {
        imageList: ['/common/1.jpg', '/common/2.jpg', '/common/3.jpg'],
        account:'',
        passWord:''
    },
    change: function(e) {
        console.log("Tab index: " + e.index);
    },
    driver(){
        console.info("00");
        router.push({
            uri:"pages/Map/Map",

        });
        console.info("020");
    },

}
