(function (ng) {
    var mod = ng.module("mainModule");
    mod.controller('mainCtrl', ['$scope',
        function ($scope) {
            $scope.myInterval = 2800;
            $scope.slides = [
                {
                    image: 'https://files.therecroom.com/entertainment/calgary-events/Dance_L2.jpg',
                    title:'Fiestas',
                    text:'Organiza tu próxima gran fiesta'
                },
                {
                    image: 'https://fotografosexpertos.files.wordpress.com/2016/06/fotografos-en-denver-caballero-1162.jpg?w=1200&h=400&crop=1',
                    title:'Bodas',
                    text:'Planea tu Boda, la fiesta más grande de tu vida'
                },
                {
                    image: 'https://lakelandhillsymca.com/dev/wp-content/uploads/parties-celebration.jpg',
                    title:'Fiestas Infantiles',
                    text:'Celebra el cumpleaños de tus hijos, con motivos y entretenimiento'
                },
                {
                    image: 'https://team.agency/wp-content/uploads/2017/12/moscu-fiesta-recomendaciones.jpg',
                    title:'Despedidas de Soltera',
                    text:'Despide la soltería de tu mejor amiga'
                },
                {
                    image: 'http://www.gugaplanet.cl/wp-content/uploads/2017/11/graduacion-1-1200x400.jpeg',
                    title:'Fiestas de grado',
                    text:'¿Cómo planeas celebrar tu graduación?'
                }
            ];
        }
    ]);
}
)(window.angular);
