(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedoresContext", "api/proveedores");
    mod.controller('proveedorNewCtrl', ['$scope', '$http', 'proveedoresContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name proveedores.controller:proveedorNewCtrl
         * @description
         * Definición del controlador auxiliar para crear Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} proveedoresContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Libros en el Backend.
         * @param {Object} $rootScope Referencia injectada al Scope definida para
         * toda la aplicación.
         */
        function ($scope, $http, proveedoresContext, $state, $rootScope) {
            $rootScope.edit = false;

            $scope.data = {};

            /**
             * @ngdoc function
             * @name createEditorial
             * @methodOf proveedores.controller:proveedorNewCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para crear el autor.
             * @param {Object} autor Objeto con el nuevo autor.
             */
            $scope.createProveedor = function () {
                $http.post(proveedoresContext, $scope.data).then(function (response) {
                    $state.go('proveedoresList', {proveedorId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);