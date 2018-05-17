(function (ng) {
    var mod = ng.module("mainModule");
    mod.controller('mainCtrl', ['$scope',
        function ($scope) {
            $scope.myInterval = 3000;
            $scope.slides = [
                {
                    image: 'http://djparatufiesta.com.mx/fondos/banners-animacion-para-fiestas-con-dj-1.jpg',
                    title:'Fiestas',
                    text:'Organiza tu proxima gran fiesta'
                },
                {
                    image: 'https://www.bezzia.com/wp-content/uploads/2017/12/trucos-para-ahorrar-en-la-boda.jpg',
                    title:'Bodas',
                    text:'Organiza la Boda, la fiesta mas grande de tu vida'
                },
                {
                    image: 'https://www.piccolafesta.com/wp-content/uploads/2017/11/Tips-para-organizar-una-fiesta-infantil.jpg',
                    title:'Fiestas Infantiles',
                    text:'Organiza las fiestas de tus hijos, con motivos y entretenimiento'
                },
                {
                    image: 'https://laopinionla.files.wordpress.com/2017/07/quinceac3b1era-vestido-original-main-picture-amaraby.jpg?quality=60&strip=all&w=940',
                    title:'Quinceañeras',
                    text:'Organiza la Quinceañera de tu hija'
                }
            ];
        }
    ]);
}
)(window.angular);
