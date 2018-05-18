(function (ng) {
    var mod = ng.module("tematicasModule");
    mod.constant("tematicasContext", "api/tematicas");
    mod.controller('tematicasCtrl', ['$scope', '$http', 'tematicasContext',
        function ($scope, $http, tematicasContext) {
            /**
         * @ngdoc controller
         * @name tematicas.controller:tematicasCtrl
         * @description
         * Definición del controlador de Angular del módulo Tematicas. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} tematicaContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Tematicas en el Backend.
         * @param {Object} clientesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Clientes en el Backend.
         */
            $http.get(tematicasContext).then(function (response) {
                $scope.tematicasRecords = response.data;
            });
        }
    ]);
}
)(window.angular);

