(function (ng) {
    var mod = ng.module("bonosModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonosDetailCtrl', ['$scope', '$http', 'bonosContext', '$state', '$filter',
        /**
         * @ngdoc controller
         * @name blogs.controller:blogDetailCtrl
         * @description
         * Definición de un controlador auxiliar del módulo Bloges. 
         * Se crea el controlador con el cual se manejan las vistas de detalle
         * del módulo.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Bloges en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, bonosContext, $state, $filter) {
            if (($state.params.bonosId !== undefined) && ($state.params.bonosId !== null)) {
                /**
                 * @ngdoc function
                 * @name getblogID
                 * @methodOf blogs.controller:blogDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el blog por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del blog o API donde se puede consultar.
                 */
                $http.get(bonosContext).then(function (response) {
                    $scope.bonosRecords = response.data;
                    $scope.currentBonos = $filter('filter')($scope.bonosRecords, {id: $state.params.bonosId}, true)[0];
                });
            }
        }
    ]);
}
)(window.angular);