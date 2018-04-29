(function (ng) {
    var mod = ng.module("bonosModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonosCtrl', ['$scope', '$http', 'bonosContext',
        /**
         * @ngdoc controller
         * @name blogs.controller:blogCtrl
         * @description
         * Definición del controlador de Angular del módulo Bloges. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} blogsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Bloges en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, bonosContext) {
            /**
             * @ngdoc function
             * @name getBlogs
             * @methodOf blogs.controller:blogCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para obtener el recurso 
             * donde se encuentran los bloges en formato JSON. El recurso
             * puede ser un archivo o un API Rest. La función se ejecuta
             * automáticamente cuando el controlador es accedido desde el
             * navegador.
             * @param {String} URL Dirección donde se encuentra el recurso
             * de los bloges o API donde se puede consultar. Se utiliza el
             * contexto definido anteriormente.
             */
            $http.get(bonosContext).then(function (response) {
                $scope.bonosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);