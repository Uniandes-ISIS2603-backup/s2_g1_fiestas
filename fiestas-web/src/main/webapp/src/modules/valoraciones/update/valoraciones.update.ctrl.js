(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionesContext", "api/valoraciones");

    mod.controller('valoracionUpdateCtrl', ['$scope', '$http', 'valoracionesContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name valoraciones.controller:valoracionUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} valoracionesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Libros en el Backend.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, valoracionesContext, $state, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idValoracion = $state.params.valoracionId;

            //Consulto el autor a editar.
            $http.get(valoracionesContext + '/' + idValoracion).then(function (response) {
                var valoracion = response.data;
                $scope.data.calificacion = valoracion.calificacion;
                $scope.data.comentario = valoracion.comentario;
            });
            
            /**
             * @ngdoc function
             * @name createValoracion
             * @methodOf valoraciones.controller:valoracionUpdateCtrl
             * @description
             * Crea un nuevo autor con los libros nuevos y la información del
             * $scope.
             */
            $scope.updateValoracion = function () {
                $http.put(valoracionesContext + "/" + idValoracion, $scope.data).then(function (response) {
                    if ($scope.selectedItems.length >= 0) {
                        $http.put(valoracionesContext + "/" + response.data.id, $scope.selectedItems).then(function (response) {
                        });
                    }
                    //Valoracion created successfully
                    $state.go('valoracionesList', {valoracionId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);