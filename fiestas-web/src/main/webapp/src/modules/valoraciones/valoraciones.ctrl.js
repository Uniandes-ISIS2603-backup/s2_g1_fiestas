(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionContext", "api/valoraciones");
    mod.controller('valoracionesCtrl', ['$scope', '$http','valoracionContext',
         /**
         * @ngdoc controller
         * @name valoracions.controller:valoracionCtrl
         * @description
         * Definición del controlador de Angular del módulo Valoraciones. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} valoracionesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Valoraciones en el Backend.
         */
       function ($scope, $http, valoracionContext) {
           $http.get(valoracionContext).then(function (response) {
               /**
             * @ngdoc function
             * @name getValoraciones
             * @methodOf valoraciones.controller:authorCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para obtener el recurso 
             * donde se encuentran los autores en formato JSON. El recurso
             * puede ser un archivo o un API Rest. La función se ejecuta
             * automáticamente cuando el controlador es accedido desde el
             * navegador.
             * @param {String} URL Dirección donde se encuentra el recurso
             * de los autores o API donde se puede consultar. Se utiliza el
             * contexto definido anteriormente.
             */
                $scope.valoracionesRecords = response.data;
            });
        }
    ]);
}
)(window.angular);